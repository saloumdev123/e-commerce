package sen.saloum.saloum_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sen.saloum.saloum_service.domain.Commande;
import sen.saloum.saloum_service.domain.LigneCommande;
import sen.saloum.saloum_service.domain.Product;
import sen.saloum.saloum_service.models.dto.*;
import sen.saloum.saloum_service.repos.CommandeRepository;
import sen.saloum.saloum_service.repos.LigneCommandeRepository;
import sen.saloum.saloum_service.repos.ProductRepository;
import sen.saloum.saloum_service.service.interfaces.ILigneCommande;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LigneCommandeService implements ILigneCommande {

    private final LigneCommandeRepository ligneCommandeRepository;
    private final ProductRepository productRepository;
    private final CommandeRepository commandeRepository;

    public List<LigneCommandeDto> getAllLignes() {
        return ligneCommandeRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public Optional<LigneCommandeDto> getLigneById(Long id) {
        return ligneCommandeRepository.findById(id)
                .map(this::mapToDto);
    }
    public LigneCommandeDto saveLigne(LigneCommandeDto ligneCommandeDto) {
        try {
            Commande commande;

            // Validate if 'commande' is null or has no ID
            if (ligneCommandeDto.getCommande() == null || ligneCommandeDto.getCommande().getId() == null) {
                Long defaultCommandeId = 1L; // Default Commande ID
                System.err.println("Commande information missing. Using default Commande ID: " + defaultCommandeId);

                commande = commandeRepository.findById(defaultCommandeId)
                        .orElseThrow(() -> new RuntimeException("Commande not found with ID " + defaultCommandeId));

                CommandeDto commandeDto = new CommandeDto();
                commandeDto.setId(defaultCommandeId);
                ligneCommandeDto.setCommande(commandeDto);
            } else {
                commande = commandeRepository.findById(ligneCommandeDto.getCommande().getId())
                        .orElseThrow(() -> new RuntimeException("Commande not found with ID " + ligneCommandeDto.getCommande().getId()));
            }

            // Retrieve or create Product
            if (ligneCommandeDto.getProduct() == null || ligneCommandeDto.getProduct().getNom() == null) {
                throw new RuntimeException("Product information is missing in LigneCommandeDto");
            }
            Product product = productRepository.findByNom(ligneCommandeDto.getProduct().getNom())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + ligneCommandeDto.getProduct().getNom()));

            LigneCommande ligneCommande = new LigneCommande();
            ligneCommande.setCommande(commande);
            ligneCommande.setProduct(product);
            ligneCommande.setQuantite(ligneCommandeDto.getQuantite());
            ligneCommande.setPrixUnitaire(product.getPrix());
            ligneCommande.setSousTotal(ligneCommandeDto.getQuantite() * product.getPrix());

            LigneCommande savedLigneCommande = ligneCommandeRepository.save(ligneCommande);

            return mapToDto(savedLigneCommande);

        } catch (Exception e) {
            System.err.println("Error while saving LigneCommande: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la création de la ligne de commande", e);
        }
    }

    public LigneCommandeDto updateLigne(Long id, LigneCommandeDto ligneCommandeDto) {
        LigneCommande existingLigneCommande = ligneCommandeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ligne de commande non trouvée avec l'ID: " + id));

        Product product = productRepository.findByNom(ligneCommandeDto.getProduct().getNom())
                .orElseThrow(() -> new IllegalArgumentException("Produit non trouvé: " + ligneCommandeDto.getProduct().getNom()));

        existingLigneCommande.setProduct(product);
        existingLigneCommande.setQuantite(ligneCommandeDto.getQuantite());
        existingLigneCommande.setPrixUnitaire(product.getPrix());
        existingLigneCommande.setSousTotal(ligneCommandeDto.getQuantite() * product.getPrix());

        LigneCommande updatedLigneCommande = ligneCommandeRepository.save(existingLigneCommande);

        return mapToDto(updatedLigneCommande);
    }

    public void deleteLigne(Long id) {
        ligneCommandeRepository.deleteById(id);
    }

    private LigneCommandeDto mapToDto(LigneCommande ligneCommande) {
        LigneCommandeDto dto = new LigneCommandeDto();
        dto.setId(ligneCommande.getId());
        dto.setProduct(new ProductDto(ligneCommande.getProduct().getId(), ligneCommande.getProduct().getNom(), ligneCommande.getProduct().getPrix()));
        dto.setQuantite(ligneCommande.getQuantite());
        dto.setPrixUnitaire(ligneCommande.getPrixUnitaire());
        dto.setSousTotal(ligneCommande.getSousTotal());

        CommandeDto commandeDto = new CommandeDto();
        commandeDto.setId(ligneCommande.getCommande().getId());
        dto.setCommande(commandeDto);

        return dto;
    }

    public LigneCommande mapToEntity(LigneCommandeDto ligneCommandeDto) {
        LigneCommande ligneCommande = new LigneCommande();
        ligneCommande.setId(ligneCommandeDto.getId());

        Product product = productRepository.findByNom(ligneCommandeDto.getProduct().getNom())
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + ligneCommandeDto.getProduct().getNom()));
        ligneCommande.setProduct(product);

        Commande commande = commandeRepository.findById(ligneCommandeDto.getCommande().getId())
                .orElseThrow(() -> new IllegalArgumentException("Commande not found: " + ligneCommandeDto.getCommande().getId()));
        ligneCommande.setCommande(commande);

        ligneCommande.setQuantite(ligneCommandeDto.getQuantite());
        ligneCommande.setPrixUnitaire(ligneCommandeDto.getPrixUnitaire());
        ligneCommande.setSousTotal(ligneCommandeDto.getSousTotal());

        return ligneCommande;
    }

}
