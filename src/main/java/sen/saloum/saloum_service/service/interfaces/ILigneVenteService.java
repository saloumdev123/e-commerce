package sen.saloum.saloum_service.service.interfaces;

import sen.saloum.saloum_service.domain.LigneVente;
import sen.saloum.saloum_service.models.dto.LigneVenteDto;

import java.util.List;

public interface ILigneVenteService {

    void deleteLigneVente(Long id);
    List<LigneVenteDto> getAllLignesVente();
    LigneVenteDto getLigneVenteById(Long id);
    LigneVenteDto saveLigneVente(LigneVenteDto ligneVenteDto);
    LigneVenteDto updateLigneVente(Long id, LigneVenteDto ligneVenteDto);
}
