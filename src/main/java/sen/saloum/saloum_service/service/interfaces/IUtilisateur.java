package sen.saloum.saloum_service.service.interfaces;

import sen.saloum.saloum_service.models.dto.UtilisateurDto;

import java.util.List;
import java.util.Optional;

public interface IUtilisateur {

    List<UtilisateurDto> getAllUtilisateurs();
    Optional<UtilisateurDto> getUtilisateurById(Long id);
    UtilisateurDto saveUtilisateur(UtilisateurDto utilisateurDto);
    void deleteUtilisateur(Long id);
    UtilisateurDto updateUser(Long id, UtilisateurDto utilisateurDto);

}
