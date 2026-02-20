package tn.esprit.twin.microservicelivraison.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.twin.microservicelivraison.model.Livraison;

import java.util.List;

public interface LivraisonRepository extends MongoRepository<Livraison, String> {

    List<Livraison> findByStatus(String status);

}