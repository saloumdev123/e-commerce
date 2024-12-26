package sen.saloum.saloum_service.service.interfaces;

import sen.saloum.saloum_service.models.dto.VenteDto;

import java.util.List;
import java.util.Optional;

public interface IVente {
    List<VenteDto> getAllVentes();
    Optional<VenteDto> getVenteById(Long id);
    VenteDto saveVente(VenteDto venteDto);
    VenteDto updateVente(Long id, VenteDto venteDto);
    void deleteVente(Long id);
}
