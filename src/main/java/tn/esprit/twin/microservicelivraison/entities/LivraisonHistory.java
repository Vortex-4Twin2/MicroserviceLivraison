package tn.esprit.twin.microservicelivraison.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "livraison_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LivraisonHistory {

    @Id
    private String id;

    private String livraisonId;
    private String ancienStatus;
    private String nouveauStatus;
    private String date;
}