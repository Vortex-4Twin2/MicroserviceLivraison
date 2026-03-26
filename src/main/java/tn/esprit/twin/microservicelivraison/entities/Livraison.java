package tn.esprit.twin.microservicelivraison.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "livraisons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Livraison {

    @Id
    private String id;

    private String orderId;
    private String adresse;
    private String ville;

    private LivraisonStatus status;

    private Double prixLivraison;
}