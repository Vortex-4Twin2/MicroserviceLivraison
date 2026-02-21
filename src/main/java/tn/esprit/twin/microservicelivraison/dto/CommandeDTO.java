package tn.esprit.twin.microservicelivraison.dto;

public class CommandeDTO {

    private String id;
    private String clientId;
    private double total;
    private String adresseLivraison;

    public CommandeDTO() {
    }

    public CommandeDTO(String id, String clientId, double total, String adresseLivraison) {
        this.id = id;
        this.clientId = clientId;
        this.total = total;
        this.adresseLivraison = adresseLivraison;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getAdresseLivraison() {
        return adresseLivraison;
    }

    public void setAdresseLivraison(String adresseLivraison) {
        this.adresseLivraison = adresseLivraison;
    }
}
