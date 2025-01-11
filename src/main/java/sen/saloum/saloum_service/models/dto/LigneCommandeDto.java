package sen.saloum.saloum_service.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sen.saloum.saloum_service.domain.Product;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LigneCommandeDto {
    private Long id;
    private ProductDto product;
    private Integer quantite;
    private Double prixUnitaire;
    private Double sousTotal;
    private CommandeDto commande;
}
