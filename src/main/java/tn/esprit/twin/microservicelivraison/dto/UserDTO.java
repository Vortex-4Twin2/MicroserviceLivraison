package tn.esprit.twin.microservicelivraison.dto;

public class UserDTO {

    private Long userId;
    private String nom;
    private String telephone;
    private String adresse;
    private String email;

    // Constructeur par défaut
    public UserDTO() {
    }

    // Constructeur avec paramètres
    public UserDTO(Long userId, String nom, String telephone, String adresse, String email) {
        this.userId = userId;
        this.nom = nom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.email = email;
    }

    // Getters
    public Long getUserId() {
        return userId;
    }

    public String getNom() {
        return nom;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getEmail() {
        return email;
    }

    // Setters
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
