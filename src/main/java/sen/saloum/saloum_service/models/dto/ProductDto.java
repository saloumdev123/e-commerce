package sen.saloum.saloum_service.models.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    @NotNull(message = "Product ID cannot be null")
    private Long id;
    @NotBlank(message = "Nom is required")
    private String nom;
    private String description;
    @NotNull(message = "Prix is required")
    @Min(value = 0, message = "Prix must be a positive number")
    private Double prix;
    private Integer quantiteEnStock;
    private String imageUrl;
    private OffsetDateTime dateAjout;
    private CategorieDto categorie;
    private List<AvieDto> avies;
    public ProductDto(Long id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public ProductDto(Long id, String nom, Double prix) {
        this.id = id;
        this.nom = nom;
        this.prix=prix;
    }
}
