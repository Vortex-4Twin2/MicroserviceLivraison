package tn.esprit.twin.microservicelivraison.workflow;

import tn.esprit.twin.microservicelivraison.entities.LivraisonStatus;

import java.util.Map;
import java.util.Set;

public class LivraisonWorkflow {

    private static final Map<LivraisonStatus, Set<LivraisonStatus>> transitions = Map.of(
            LivraisonStatus.EN_ATTENTE, Set.of(LivraisonStatus.EN_COURS, LivraisonStatus.ANNULEE),
            LivraisonStatus.EN_COURS, Set.of(LivraisonStatus.LIVREE, LivraisonStatus.ANNULEE)
    );

    public static boolean isValidTransition(LivraisonStatus current, LivraisonStatus next) {
        return transitions.getOrDefault(current, Set.of()).contains(next);
    }
}