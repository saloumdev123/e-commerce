package sen.saloum.saloum_service.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvieDto {

    private Long id;
    private String commentaire;
    private Integer note;
    private OffsetDateTime dateCreation;
    private ProductDto produit;
    private UtilisateurDto utilisateur;

}
