package tn.esprit.twin.microservicelivraison.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.twin.microservicelivraison.entities.LivraisonHistory;

public interface LivraisonHistoryRepository extends MongoRepository<LivraisonHistory, String> {
}