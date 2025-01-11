package sen.saloum.saloum_service.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PanierDto {

    private Long id;
    private Long utilisateurId;
    private List<LignePanierDto> lignes;
}