package tn.esprit.twin.microservicelivraison.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.twin.microservicelivraison.entities.LivraisonHistory;

@Repository
public interface LivraisonHistoryRepository extends MongoRepository<LivraisonHistory, String> {
}