package sen.saloum.saloum_service.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtilisateurDto {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String adresse;
    private String telephone;
    private String motDePasse;
    private List<AvieDto> avies;
    // UtilisateurDto constructor
    public UtilisateurDto(Long id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public UtilisateurDto(Long id, String nom, String prenom, String telephone, String adresse, String email, String motDePasse) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.email = email;
        this.motDePasse = motDePasse;
    }
}