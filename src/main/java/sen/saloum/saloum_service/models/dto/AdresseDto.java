package sen.saloum.saloum_service.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sen.saloum.saloum_service.models.TypeAdresse;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdresseDto {
    private Long id;
    private TypeAdresse type;
    private String rue;
    private String ville;
    private String codePostal;
    private String pays;
}
