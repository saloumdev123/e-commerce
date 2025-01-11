package sen.saloum.saloum_service.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sen.saloum.saloum_service.domain.Product;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LignePanierDto {
    private Long id;
    private Long  productId;
    private Integer quantite;
    private Double prixUnitaire;
    private Double sousTotal;
}
