package sen.saloum.saloum_service.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LigneVenteDto {
    private Long id;
    private String nom;
    private Integer quantite;
    private Double prixUnitaire;
    private Double sousTotal;
    private VenteDto vente;
}
