package sen.saloum.saloum_service.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sen.saloum.saloum_service.models.enums.StatutCommande;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommandeDto {

    private Long id;
    private OffsetDateTime dateCommande;
    private Double montantTotal;
    private StatutCommande statut;
    private UtilisateurDto utilisateurDto;
    private List<LigneCommandeDto> lignes;
}
