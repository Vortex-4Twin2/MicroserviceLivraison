package tn.esprit.twin.microservicelivraison.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.twin.microservicelivraison.entities.Livraison;
import tn.esprit.twin.microservicelivraison.entities.LivraisonHistory;
import tn.esprit.twin.microservicelivraison.entities.LivraisonStatus;
import tn.esprit.twin.microservicelivraison.entities.UserClient;
import tn.esprit.twin.microservicelivraison.repository.LivraisonHistoryRepository;
import tn.esprit.twin.microservicelivraison.repository.LivraisonRepository;
import tn.esprit.twin.microservicelivraison.dto.CommandeDTO;
import tn.esprit.twin.microservicelivraison.dto.UserDTO;
import tn.esprit.twin.microservicelivraison.dto.LivraisonEventDTO;
import tn.esprit.twin.microservicelivraison.workflow.LivraisonWorkflow;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LivraisonService implements ILivraisonService {

    private final LivraisonRepository repository;
    private final UserClient userClient;
    private final LivraisonHistoryRepository historyRepository;
    private final LivraisonProducer livraisonProducer;
    private final EmailService emailService;

    @Override
    public Livraison createLivraison(Livraison livraison) {

        if (livraison.getDateLivraison() != null &&
                livraison.getDateLivraison().isAfter(LocalDateTime.now())) {

            livraison.setStatus(LivraisonStatus.PLANIFIEE);

        } else {
            livraison.setStatus(LivraisonStatus.EN_ATTENTE);
        }

        Livraison saved = repository.save(livraison);

        LivraisonEventDTO event = new LivraisonEventDTO(
                saved.getId(),
                saved.getOrderId(),
                saved.getAdresse(),
                saved.getStatus().name(),
                saved.getPrixLivraison()
        );

        livraisonProducer.sendLivraison(event);

        return saved;
    }

    public Livraison updateStatus(String id, LivraisonStatus newStatus) {

        Livraison liv = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livraison introuvable"));

        LivraisonStatus oldStatus = liv.getStatus();

        System.out.println("OLD STATUS = " + oldStatus);
        System.out.println("NEW STATUS = " + newStatus);

        if (oldStatus == LivraisonStatus.LIVREE) {
            throw new RuntimeException("Déjà livrée !");
        }

        if (oldStatus == LivraisonStatus.ANNULEE) {
            throw new RuntimeException("Livraison annulée !");
        }

        if (!LivraisonWorkflow.isValidTransition(oldStatus, newStatus)) {
            throw new RuntimeException(
                    "Transition invalide entre " + oldStatus + " et " + newStatus
            );
        }

        if (newStatus == LivraisonStatus.LIVREE && liv.getAdresse() == null) {
            throw new RuntimeException("Adresse obligatoire pour livrer !");
        }

        // 🔥 Kafka + EMAIL
        if (oldStatus != LivraisonStatus.LIVREE
                && newStatus == LivraisonStatus.LIVREE) {

            LivraisonEventDTO event = new LivraisonEventDTO(
                    liv.getId(),
                    liv.getOrderId(),
                    liv.getAdresse(),
                    newStatus.name(),
                    liv.getPrixLivraison()
            );

            livraisonProducer.sendLivraison(event);

            // =========================
            // ✅ EMAIL LOGIC
            // =========================
            try {
                if (liv.getUserId() != null) {

                    UserDTO user = userClient.getUserById(liv.getUserId());

                    if (user != null && user.getEmail() != null) {

                        emailService.sendEmail(
                                user.getEmail(),
                                "📦 Livraison effectuée",
                                "Votre commande " + liv.getOrderId() +
                                        " est livrée à l'adresse : " + liv.getAdresse() + " ✅"
                        );
                    }
                }

            } catch (Exception e) {
                System.out.println("❌ Email error: " + e.getMessage());
            }
        }

        liv.setStatus(newStatus);

        try {
            historyRepository.save(new LivraisonHistory(
                    null,
                    liv.getId(),
                    oldStatus.name(),
                    newStatus.name(),
                    LocalDateTime.now().toString()
            ));
        } catch (Exception e) {
            System.out.println("❌ History error: " + e.getMessage());
        }

        return repository.save(liv);
    }

    public List<LivraisonHistory> getHistory(String livraisonId) {
        return historyRepository.findAll()
                .stream()
                .filter(h -> h.getLivraisonId().equals(livraisonId))
                .toList();
    }

    // =========================
    // EXISTING CODE (UNCHANGED)
    // =========================

    public Livraison createLivraisonFromCommande(CommandeDTO commandeDTO) {

        Livraison livraison = new Livraison();

        livraison.setOrderId(commandeDTO.getId());
        livraison.setAdresse(commandeDTO.getAdresseLivraison());
        livraison.setStatus(LivraisonStatus.EN_ATTENTE);
        livraison.setPrixLivraison(commandeDTO.getTotal() * 0.1);

        return repository.save(livraison);
    }

    public Livraison createLivraisonFromUser(Long userId) {

        UserDTO user = userClient.getUserById(userId);

        Livraison livraison = new Livraison();
        livraison.setOrderId("NO_ORDER");
        livraison.setAdresse(user.getAdresse());
        livraison.setVille("Tunis");
        livraison.setStatus(LivraisonStatus.EN_ATTENTE);
        livraison.setPrixLivraison(8.0);

        // ✅ NEW
        livraison.setUserId(userId);

        return repository.save(livraison);
    }

    @Override
    public List<Livraison> getAllLivraisons() {
        return repository.findAll();
    }

    @Override
    public Livraison getLivraisonById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Livraison updateLivraison(String id, Livraison updated) {
        Livraison livraison = repository.findById(id).orElse(null);

        if (livraison != null) {
            livraison.setAdresse(updated.getAdresse());
            livraison.setVille(updated.getVille());
            livraison.setStatus(updated.getStatus());
            livraison.setPrixLivraison(updated.getPrixLivraison());
            return repository.save(livraison);
        }
        return null;
    }

    @Override
    public void deleteLivraison(String id) {
        repository.deleteById(id);
    }
}