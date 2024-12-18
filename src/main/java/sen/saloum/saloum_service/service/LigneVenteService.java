package sen.saloum.saloum_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sen.saloum.saloum_service.domain.Categorie;
import sen.saloum.saloum_service.domain.LigneVente;
import sen.saloum.saloum_service.domain.Product;
import sen.saloum.saloum_service.domain.Vente;
import sen.saloum.saloum_service.models.dto.LigneVenteDto;
import sen.saloum.saloum_service.models.dto.VenteDto;
import sen.saloum.saloum_service.repos.CategorieRepository;
import sen.saloum.saloum_service.repos.LigneVenteRepository;
import sen.saloum.saloum_service.repos.ProductRepository;
import sen.saloum.saloum_service.repos.VenteRepository;
import sen.saloum.saloum_service.service.interfaces.ILigneVenteService;


import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LigneVenteService implements ILigneVenteService {

    private final LigneVenteRepository ligneVenteRepository;
    private final ProductRepository productRepository;
    private final CategorieRepository categorieRepository;
    private final VenteRepository venteRepository;

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
        try {
            Vente vente;  // Declare vente outside the if-else block

            // Validate if 'vente' is null or has no ID
            if (ligneVenteDto.getVente() == null || ligneVenteDto.getVente().getId() == null) {
                // Set a default venteId or throw an exception
                Long defaultVenteId = 1L; // Or retrieve this from another source, like the logged-in user, etc.
                System.err.println("Vente information missing. Using default Vente ID: " + defaultVenteId);

                // Retrieve existing Vente entity using default ID
                vente = venteRepository.findById(defaultVenteId)
                        .orElseThrow(() -> new RuntimeException("Vente not found with ID " + defaultVenteId));

                // Set the Vente object in the LigneVenteDto
                VenteDto venteDto = new VenteDto();
                venteDto.setId(defaultVenteId);
                ligneVenteDto.setVente(venteDto); // Set the default VenteDto back to the DTO
            } else {
                // Retrieve existing Vente entity using provided ID
                vente = venteRepository.findById(ligneVenteDto.getVente().getId())
                        .orElseThrow(() -> new RuntimeException("Vente not found with ID " + ligneVenteDto.getVente().getId()));
            }

            // Retrieve or create Product
            Product product = productRepository.findByNom(ligneVenteDto.getNom())
                    .orElseGet(() -> {
                        Product newProduct = new Product();
                        newProduct.setNom(ligneVenteDto.getNom());
                        newProduct.setDescription("Default description for " + ligneVenteDto.getNom());
                        newProduct.setPrix(ligneVenteDto.getPrixUnitaire());
                        newProduct.setQuantiteEnStock(100);
                        newProduct.setDateAjout(OffsetDateTime.now());
                        Categorie defaultCategorie = categorieRepository.findById(1L)
                                .orElseThrow(() -> new RuntimeException("Default category not found"));
                        newProduct.setCategorie(defaultCategorie);
                        return productRepository.save(newProduct);
                    });

            LigneVente ligneVente = new LigneVente();
            ligneVente.setVente(vente);
            ligneVente.setProduct(product);
            ligneVente.setQuantite(ligneVenteDto.getQuantite());
            ligneVente.setPrixUnitaire(product.getPrix());
            ligneVente.setSousTotal(ligneVenteDto.getQuantite() * product.getPrix());

            LigneVente savedLigneVente = ligneVenteRepository.save(ligneVente);

            return mapToDto(savedLigneVente);

        } catch (Exception e) {
            System.err.println("Error while saving LigneVente: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la création de la ligne de vente", e);
        }
    }

    public LigneVenteDto updateLigneVente(Long id, LigneVenteDto ligneVenteDto) {
        // Find the existing LigneVente by ID
        LigneVente existingLigneVente = ligneVenteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ligne de vente non trouvée avec l'ID: " + id));

        // Update the fields of the LigneVente entity
        Product product = productRepository.findByNom(ligneVenteDto.getNom())
                .orElseThrow(() -> new IllegalArgumentException("Produit non trouvé: " + ligneVenteDto.getNom()));

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

    LigneVenteDto mapToDto(LigneVente ligneVente) {
        LigneVenteDto dto = new LigneVenteDto();
        dto.setId(ligneVente.getId());
        dto.setNom(ligneVente.getProduct().getNom());
        dto.setQuantite(ligneVente.getQuantite());
        dto.setPrixUnitaire(ligneVente.getPrixUnitaire());
        dto.setSousTotal(ligneVente.getSousTotal());
        return dto;
    }

    public LigneVente mapToEntity(LigneVenteDto ligneVenteDto) {
        LigneVente ligneVente = new LigneVente();
        ligneVente.setId(ligneVenteDto.getId());
        Product product =  productRepository.findByNom(ligneVenteDto.getNom())
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + ligneVenteDto.getNom()));
        ligneVente.setProduct(product);
        ligneVente.setQuantite(ligneVenteDto.getQuantite());
        ligneVente.setPrixUnitaire(ligneVenteDto.getPrixUnitaire());
        ligneVente.setSousTotal(ligneVenteDto.getSousTotal());
        return ligneVente;
    }
}
