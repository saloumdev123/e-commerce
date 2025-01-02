package sen.saloum.saloum_service.models.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockeDto {
    private Long id;

    @NotNull(message = "La quantité disponible est obligatoire")
    @Min(value = 0, message = "La quantité disponible doit être un nombre positif")
    private Integer quantiteDisponible;

    @NotNull(message = "Le seuil d'alerte est obligatoire")
    @Min(value = 1, message = "Le seuil d'alerte doit être supérieur ou égal à 1")
    private Integer seuilAlerte;
    @NotNull(message = "L'ID du produit est obligatoire")
    private Long product;
}