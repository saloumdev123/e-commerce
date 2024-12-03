package sen.saloum.saloum_service.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LigneCommandeDto {
    private Long id;
    private String productName;
    private Integer quantite;
    private Double prixUnitaire;
    private Double sousTotal;
}
