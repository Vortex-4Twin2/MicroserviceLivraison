package tn.esprit.twin.microservicelivraison.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "livraisons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Livraison {

    @Id
    private String id;

    private String orderId;
    private String adresse;
    private String ville;
    private String status; // EN_PREPARATION, EN_COURS, LIVREE
    private Double prixLivraison;
}