package tn.esprit.twin.microservicelivraison.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.twin.microservicelivraison.model.Livraison;

public interface LivraisonRepository extends MongoRepository<Livraison, String> {
}
