package sen.saloum.saloum_service.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sen.saloum.saloum_service.models.enums.VenteStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VenteDto {
    private Long id;
    private LocalDateTime dateVente;
    private Double montantTotal;
    private VenteStatus statut;
    private UtilisateurDto client;
    private List<LigneVenteDto> lignes;
}