package tn.esprit.twin.microservicelivraison.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tn.esprit.twin.microservicelivraison.entities.Livraison;
import tn.esprit.twin.microservicelivraison.entities.LivraisonStatus;
import tn.esprit.twin.microservicelivraison.repository.LivraisonRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LivraisonScheduler {

    private final LivraisonRepository repository;

    @Scheduled(fixedRate = 60000)
    public void checkLivraisonsPlanifiees() {

        System.out.println("⏱️ Scheduler running...");

        List<Livraison> list = repository.findByStatus(LivraisonStatus.PLANIFIEE);

        for (Livraison liv : list) {

            if (liv.getDateLivraison() != null &&
                    liv.getDateLivraison().isBefore(LocalDateTime.now())) {

                liv.setStatus(LivraisonStatus.EN_ATTENTE);
                repository.save(liv);

                System.out.println("📦 Livraison activée automatiquement: " + liv.getId());
            }
        }
    }
}