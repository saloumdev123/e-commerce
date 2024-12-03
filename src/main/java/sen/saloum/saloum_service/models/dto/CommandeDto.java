package sen.saloum.saloum_service.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sen.saloum.saloum_service.models.StatutCommande;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommandeDto {
    private Long id;
    private LocalDateTime dateCommande;
    private Double montantTotal;
    private StatutCommande statut;

    private List<LigneCommandeDto> lignes; // Use standalone LigneCommandeNestedDto
}
