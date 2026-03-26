package tn.esprit.twin.microservicelivraison.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.twin.microservicelivraison.entities.Livraison;
import tn.esprit.twin.microservicelivraison.entities.UserClient;
import tn.esprit.twin.microservicelivraison.repository.LivraisonRepository;
import tn.esprit.twin.microservicelivraison.dto.CommandeDTO;
import tn.esprit.twin.microservicelivraison.dto.UserDTO;
import tn.esprit.twin.microservicelivraison.dto.LivraisonEventDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LivraisonService implements ILivraisonService {

    private final LivraisonRepository repository;
    private final UserClient userClient;

    // ✅ Kafka Producer
    private final LivraisonProducer livraisonProducer;

    @Override
    public Livraison createLivraison(Livraison livraison) {

        livraison.setStatus("LIVREE"); // pour test Kafka

        Livraison saved = repository.save(livraison);

        // 🔥 Envoi Kafka
        LivraisonEventDTO event = new LivraisonEventDTO(
                saved.getId(),
                saved.getOrderId(),
                saved.getAdresse(),
                saved.getStatus(),
                saved.getPrixLivraison()
        );

        livraisonProducer.sendLivraison(event);

        return saved;
    }

    // 🔥 RabbitMQ (existant - NE PAS TOUCHER)
    public Livraison createLivraisonFromCommande(CommandeDTO commandeDTO) {

        Livraison livraison = new Livraison();

        livraison.setOrderId(commandeDTO.getId());
        livraison.setAdresse(commandeDTO.getAdresseLivraison());
        livraison.setStatus("EN_PREPARATION");
        livraison.setPrixLivraison(commandeDTO.getTotal() * 0.1);

        return repository.save(livraison);
    }

    // 🔥 Feign (existant)
    public Livraison createLivraisonFromUser(Long userId) {

        UserDTO user = userClient.getUserById(userId);

        Livraison livraison = new Livraison();
        livraison.setOrderId(null);
        livraison.setAdresse(user.getAdresse());
        livraison.setVille("Tunis");
        livraison.setStatus("EN_PREPARATION");
        livraison.setPrixLivraison(8.0);

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