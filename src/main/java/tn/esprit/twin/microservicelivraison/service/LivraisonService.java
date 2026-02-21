package tn.esprit.twin.microservicelivraison.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.twin.microservicelivraison.model.Livraison;
import tn.esprit.twin.microservicelivraison.repository.LivraisonRepository;
import tn.esprit.twin.microservicelivraison.dto.CommandeDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LivraisonService implements ILivraisonService {

    private final LivraisonRepository repository;

    @Override
    public Livraison createLivraison(Livraison livraison) {
        livraison.setStatus("EN_PREPARATION");
        return repository.save(livraison);
    }

    // 🔥 Nouvelle méthode appelée par RabbitMQ
    public Livraison createLivraisonFromCommande(CommandeDTO commandeDTO) {

        Livraison livraison = new Livraison();

        livraison.setOrderId(commandeDTO.getId());
        livraison.setAdresse(commandeDTO.getAdresseLivraison());
        livraison.setStatus("EN_PREPARATION");

        // Exemple simple : prix livraison = 10% du total
        livraison.setPrixLivraison(commandeDTO.getTotal() * 0.1);

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