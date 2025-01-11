package sen.saloum.saloum_service.service.interfaces;

import sen.saloum.saloum_service.models.dto.CommandeDto;
import java.util.List;

public interface ICommande {

    CommandeDto createCommande(CommandeDto commandeDto);
    CommandeDto getCommandeById(Long id);
    CommandeDto updateCommande(Long id, CommandeDto commandeDto);
    List<CommandeDto> getAllCommandes();
    void deleteCommande(Long id);

}
