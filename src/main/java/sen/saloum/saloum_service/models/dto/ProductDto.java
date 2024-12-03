package sen.saloum.saloum_service.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String nom;
    private String description;
    private Double prix;
    private Integer quantiteEnStock;
    private String imageUrl;
    private LocalDateTime dateAjout;

    private CategorieDto categorie;
}
