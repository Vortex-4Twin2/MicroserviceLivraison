package tn.esprit.twin.microservicelivraison.service;

import tn.esprit.twin.microservicelivraison.model.Livraison;

import java.util.List;

public interface ILivraisonService {

    Livraison createLivraison(Livraison livraison);

    List<Livraison> getAllLivraisons();

    Livraison getLivraisonById(String id);

    Livraison updateLivraison(String id, Livraison updated);

    void deleteLivraison(String id);
}