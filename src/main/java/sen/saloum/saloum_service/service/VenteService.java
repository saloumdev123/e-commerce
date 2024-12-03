package sen.saloum.saloum_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sen.saloum.saloum_service.domain.Vente;
import sen.saloum.saloum_service.models.dto.VenteDto;
import sen.saloum.saloum_service.repos.VenteRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VenteService {

    private final VenteRepository venteRepository;
    private final UtilisateurService utilisateurService; // Assuming you have a service for Utilisateur
    private final LigneVenteService ligneVenteService; // Assuming you have a service for LigneVente

    // Retrieve all ventes
    public List<VenteDto> getAllVentes() {
        return venteRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Retrieve a vente by ID
    public Optional<VenteDto> getVenteById(Long id) {
        return venteRepository.findById(id)
                .map(this::mapToDto);
    }

    // Create or update a vente
    @Transactional
    public VenteDto saveVente(VenteDto venteDto) {
        Vente vente = mapToEntity(venteDto);
        Vente savedVente = venteRepository.save(vente);
        return mapToDto(savedVente);
    }

    // Delete a vente by ID
    public void deleteVente(Long id) {
        venteRepository.deleteById(id);
    }

    // Map Vente entity to VenteDto
    public VenteDto mapToDto(Vente vente) {
        return new VenteDto(
                vente.getId(),
                vente.getDateVente(),
                vente.getMontantTotal(),
                vente.getStatut(),
                utilisateurService.mapToDto(vente.getClient()), // Nested mapping for Utilisateur
                vente.getLignes().stream()
                        .map(ligneVenteService::mapToDto)
                        .collect(Collectors.toList()) // Nested mapping for LigneVente
        );
    }

    // Map VenteDto to Vente entity
    public Vente mapToEntity(VenteDto venteDto) {
        Vente vente = new Vente();
        vente.setId(venteDto.getId());
        vente.setDateVente(venteDto.getDateVente());
        vente.setMontantTotal(venteDto.getMontantTotal());
        vente.setStatut(venteDto.getStatut());
        vente.setClient(utilisateurService.mapToEntity(venteDto.getClient())); // Nested mapping for Utilisateur
        vente.setLignes(venteDto.getLignes().stream()
                .map(ligneVenteService::mapToEntity)
                .collect(Collectors.toList())); // Nested mapping for LigneVente
        return vente;
    }
}
