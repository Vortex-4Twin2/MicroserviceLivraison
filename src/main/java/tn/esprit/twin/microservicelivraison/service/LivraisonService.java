package tn.esprit.twin.microservicelivraison.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.twin.microservicelivraison.model.Livraison;
import tn.esprit.twin.microservicelivraison.repository.LivraisonRepository;


import java.util.List;

@Service
@RequiredArgsConstructor
public class LivraisonService {

    private final LivraisonRepository repository;

    public Livraison createLivraison(Livraison livraison) {
        livraison.setStatus("EN_PREPARATION");
        return repository.save(livraison);
    }

    public List<Livraison> getAllLivraisons() {
        return repository.findAll();
    }

    public Livraison getLivraisonById(String id) {
        return repository.findById(id).orElse(null);
    }

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

    public void deleteLivraison(String id) {
        repository.deleteById(id);
    }
}