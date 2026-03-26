package tn.esprit.twin.microservicelivraison.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LivraisonEventDTO {

    private String id;
    private String orderId;
    private String adresse;
    private String status;
    private Double prixLivraison;
}
