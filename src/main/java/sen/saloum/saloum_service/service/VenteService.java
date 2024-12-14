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
    private final UtilisateurService utilisateurService;
    private final LigneVenteService ligneVenteService;

    public List<VenteDto> getAllVentes() {
        return venteRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public Optional<VenteDto> getVenteById(Long id) {
        return venteRepository.findById(id)
                .map(this::mapToDto);
    }

    @Transactional
    public VenteDto saveVente(VenteDto venteDto) {
        Vente vente = mapToEntity(venteDto);
        Vente savedVente = venteRepository.save(vente);
        return mapToDto(savedVente);
    }

    @Transactional
    public VenteDto updateVente(Long id, VenteDto venteDto) {
        Vente existingVente = venteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vente introuvable avec l'ID: " + id));

        existingVente.setDateVente(venteDto.getDateVente());
        existingVente.setMontantTotal(venteDto.getMontantTotal());
        existingVente.setStatut(venteDto.getStatut());
        existingVente.setClient(utilisateurService.mapToEntity(venteDto.getClient()));

        // Handle null case for lignes
        existingVente.setLignes(Optional.ofNullable(venteDto.getLignes())
                .orElse(List.of())
                .stream()
                .map(ligneVenteService::mapToEntity)
                .collect(Collectors.toList()));

        Vente updatedVente = venteRepository.save(existingVente);
        return mapToDto(updatedVente);
    }


    public void deleteVente(Long id) {
        venteRepository.deleteById(id);
    }


    public VenteDto mapToDto(Vente vente) {
        return new VenteDto(
                vente.getId(),
                vente.getDateVente(),
                vente.getMontantTotal(),
                vente.getStatut(),
                utilisateurService.mapToDto(vente.getClient()),
                vente.getLignes().stream()
                        .map(ligneVenteService::mapToDto)
                        .collect(Collectors.toList())
        );
    }


    public Vente mapToEntity(VenteDto venteDto) {
        Vente vente = new Vente();
        vente.setId(venteDto.getId());
        vente.setDateVente(venteDto.getDateVente());
        vente.setMontantTotal(venteDto.getMontantTotal());
        vente.setStatut(venteDto.getStatut());
        vente.setClient(utilisateurService.mapToEntity(venteDto.getClient()));

        // Handle null case for lignes
        vente.setLignes(Optional.ofNullable(venteDto.getLignes())
                .orElse(List.of())
                .stream()
                .map(ligneVenteService::mapToEntity)
                .collect(Collectors.toList()));

        return vente;
    }

}
