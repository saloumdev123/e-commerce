package sen.saloum.saloum_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sen.saloum.saloum_service.domain.LigneVente;
import sen.saloum.saloum_service.models.dto.LigneVenteDto;
import sen.saloum.saloum_service.repos.LigneVenteRepository;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LigneVenteService {

    private final LigneVenteRepository ligneVenteRepository;

    // Retrieve all LigneVente
    public List<LigneVenteDto> getAllLignesVente() {
        return ligneVenteRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Retrieve LigneVente by ID
    public LigneVenteDto getLigneVenteById(Long id) {
        return ligneVenteRepository.findById(id)
                .map(this::mapToDto)
                .orElse(null);
    }

    // Create or update a LigneVente
    public LigneVenteDto saveLigneVente(LigneVenteDto ligneVenteDto) {
        LigneVente ligneVente = mapToEntity(ligneVenteDto);
        LigneVente savedLigneVente = ligneVenteRepository.save(ligneVente);
        return mapToDto(savedLigneVente);
    }

    // Delete a LigneVente by ID
    public void deleteLigneVente(Long id) {
        ligneVenteRepository.deleteById(id);
    }

    // Map LigneVente entity to LigneVenteDto
    public LigneVenteDto mapToDto(LigneVente ligneVente) {
        return new LigneVenteDto(
                ligneVente.getId(),
                ligneVente.getProduct().getNom(), // Assuming you have a Product entity with a 'nom' field
                ligneVente.getQuantite(),
                ligneVente.getPrixUnitaire(),
                ligneVente.getSousTotal()
        );
    }

    // Map LigneVenteDto to LigneVente entity
    public LigneVente mapToEntity(LigneVenteDto ligneVenteDto) {
        LigneVente ligneVente = new LigneVente();
        ligneVente.setId(ligneVenteDto.getId());
        // Assuming you have a Product entity and you're looking up by name or id
        // ligneVente.setProduct(product);
        ligneVente.setQuantite(ligneVenteDto.getQuantite());
        ligneVente.setPrixUnitaire(ligneVenteDto.getPrixUnitaire());
        ligneVente.setSousTotal(ligneVenteDto.getSousTotal());
        return ligneVente;
    }
}
