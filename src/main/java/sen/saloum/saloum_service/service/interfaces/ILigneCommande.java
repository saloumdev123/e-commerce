package sen.saloum.saloum_service.service.interfaces;

import sen.saloum.saloum_service.models.dto.LigneCommandeDto;

import java.util.List;
import java.util.Optional;

public interface ILigneCommande {
    List<LigneCommandeDto> getAllLignes();
    Optional<LigneCommandeDto> getLigneById(Long id);
    LigneCommandeDto saveLigne(LigneCommandeDto ligneCommandeDto);
    LigneCommandeDto updateLigne(Long id, LigneCommandeDto ligneCommandeDto);
    void deleteLigne(Long id);
}
