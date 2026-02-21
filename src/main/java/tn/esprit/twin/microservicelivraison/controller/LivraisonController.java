package tn.esprit.twin.microservicelivraison.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.twin.microservicelivraison.entities.Livraison;
import tn.esprit.twin.microservicelivraison.service.ILivraisonService;
import tn.esprit.twin.microservicelivraison.dto.CommandeDTO;
import tn.esprit.twin.microservicelivraison.service.LivraisonService;

import java.util.List;

@RestController
@RequestMapping("/livraisons")
@RequiredArgsConstructor
@CrossOrigin
public class LivraisonController {

    private final ILivraisonService service;

    // =========================
    // CRUD NORMAL
    // =========================

    @PostMapping
    public Livraison create(@RequestBody Livraison livraison) {
        return service.createLivraison(livraison);
    }

    @GetMapping
    public List<Livraison> getAll() {
        return service.getAllLivraisons();
    }

    @GetMapping("/{id}")
    public Livraison getById(@PathVariable String id) {
        return service.getLivraisonById(id);
    }

    @PutMapping("/{id}")
    public Livraison update(@PathVariable String id, @RequestBody Livraison livraison) {
        return service.updateLivraison(id, livraison);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.deleteLivraison(id);
    }

    // =========================
    // 🟢 ENDPOINT TEST CONSUMER (ASYNC SIMULATION)
    // =========================

    @PostMapping("/test-consumer")
    public Livraison testConsumer(@RequestBody CommandeDTO commandeDTO) {

        Livraison livraison = new Livraison();
        livraison.setOrderId(commandeDTO.getId());
        livraison.setAdresse(commandeDTO.getAdresseLivraison());
        livraison.setVille("Tunis");
        livraison.setStatus("EN_PREPARATION");
        livraison.setPrixLivraison(8.0);

        return service.createLivraison(livraison);
    }

    // =========================
    // 🟢 NOUVEL ENDPOINT SYNCHRONE AVEC USER (Feign)
    // =========================

    @PostMapping("/from-user/{userId}")
    public Livraison createFromUser(@PathVariable Long userId) {

        // Cast vers implémentation pour accéder à la nouvelle méthode
        LivraisonService livraisonService = (LivraisonService) service;

        return livraisonService.createLivraisonFromUser(userId);
    }
}