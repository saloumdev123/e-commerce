package sen.saloum.saloum_service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvieDto {

    private Long id;
    private String commentaire;
    private Integer note; // Évaluation entre 1 et 5
    private LocalDateTime dateCreation;
    private Long produitId; // ID du produit associé
    private Long utilisateurId; // ID de l'utilisateur (auteur de l'avis)

}
