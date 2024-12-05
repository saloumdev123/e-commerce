package sen.saloum.saloum_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sen.saloum.saloum_service.domain.LigneVente;
import sen.saloum.saloum_service.domain.Product;
import sen.saloum.saloum_service.models.dto.LigneVenteDto;
import sen.saloum.saloum_service.repos.LigneVenteRepository;
import sen.saloum.saloum_service.repos.ProductRepository;
import sen.saloum.saloum_service.service.interfaces.ILigneVenteService;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LigneVenteService implements ILigneVenteService {

    private final LigneVenteRepository ligneVenteRepository;
    private final ProductRepository productRepository;

    public List<LigneVenteDto> getAllLignesVente() {
        return ligneVenteRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public LigneVenteDto getLigneVenteById(Long id) {
        return ligneVenteRepository.findById(id)
                .map(this::mapToDto)
                .orElse(null);
    }

    public LigneVenteDto saveLigneVente(LigneVenteDto ligneVenteDto) {
        LigneVente ligneVente = mapToEntity(ligneVenteDto);
        LigneVente savedLigneVente = ligneVenteRepository.save(ligneVente);
        return mapToDto(savedLigneVente);
    }
    public LigneVenteDto updateLigneVente(Long id, LigneVenteDto ligneVenteDto) {
        // Find the existing LigneVente by ID
        LigneVente existingLigneVente = ligneVenteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ligne de vente non trouvée avec l'ID: " + id));

        // Update the fields of the LigneVente entity
        Product product = productRepository.findByNom(ligneVenteDto.getProductName())
                .orElseThrow(() -> new IllegalArgumentException("Produit non trouvé: " + ligneVenteDto.getProductName()));

        existingLigneVente.setProduct(product);
        existingLigneVente.setQuantite(ligneVenteDto.getQuantite());
        existingLigneVente.setPrixUnitaire(ligneVenteDto.getPrixUnitaire());
        existingLigneVente.setSousTotal(ligneVenteDto.getSousTotal());

        // Save the updated LigneVente
        LigneVente updatedLigneVente = ligneVenteRepository.save(existingLigneVente);

        // Map the updated entity to DTO and return it
        return mapToDto(updatedLigneVente);
    }


    public void deleteLigneVente(Long id) {
        ligneVenteRepository.deleteById(id);
    }

    public LigneVenteDto mapToDto(LigneVente ligneVente) {
        return new LigneVenteDto(
                ligneVente.getId(),
                ligneVente.getProduct().getNom(),
                ligneVente.getQuantite(),
                ligneVente.getPrixUnitaire(),
                ligneVente.getSousTotal()
        );
    }

    public LigneVente mapToEntity(LigneVenteDto ligneVenteDto) {
        LigneVente ligneVente = new LigneVente();
        ligneVente.setId(ligneVenteDto.getId());
        Product product =  productRepository.findByNom(ligneVenteDto.getProductName())
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + ligneVenteDto.getProductName()));
        ligneVente.setProduct(product);
        ligneVente.setQuantite(ligneVenteDto.getQuantite());
        ligneVente.setPrixUnitaire(ligneVenteDto.getPrixUnitaire());
        ligneVente.setSousTotal(ligneVenteDto.getSousTotal());
        return ligneVente;
    }
}
