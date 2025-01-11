package sen.saloum.saloum_service.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sen.saloum.saloum_service.domain.LignePanier;
import sen.saloum.saloum_service.domain.Panier;
import sen.saloum.saloum_service.domain.Product;
import sen.saloum.saloum_service.models.dto.LignePanierDto;
import sen.saloum.saloum_service.repos.PanierRepository;
import sen.saloum.saloum_service.repos.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LignePanierService {

    private final PanierRepository panierRepository;
    private final ProductRepository productRepository;

    public LignePanierService(PanierRepository panierRepository, ProductRepository productRepository) {
        this.panierRepository = panierRepository;
        this.productRepository = productRepository;
    }

    // Ajouter une ligne au panier
    public LignePanierDto addLigneToPanier(Long panierId, Long productId, Integer quantite) {
        Panier panier = panierRepository.findById(panierId)
                .orElseThrow(() -> new RuntimeException("Panier non trouvé"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        LignePanier lignePanier = new LignePanier();
        lignePanier.setPanier(panier);
        lignePanier.setProduct(product);
        lignePanier.setQuantite(quantite);
        lignePanier.setPrixUnitaire(product.getPrix());
        lignePanier.setSousTotal(product.getPrix() * quantite);

        panier.getLignes().add(lignePanier);
        panierRepository.save(panier);

        return mapLignePanierToDto(lignePanier);
    }

    // Supprimer une ligne du panier
    public LignePanierDto removeLigneFromPanier(Long panierId, Long ligneId) {
        Panier panier = panierRepository.findById(panierId)
                .orElseThrow(() -> new RuntimeException("Panier non trouvé"));

        LignePanier lignePanier = panier.getLignes().stream()
                .filter(l -> l.getId().equals(ligneId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Ligne de panier non trouvée"));

        panier.getLignes().remove(lignePanier);
        panierRepository.save(panier);

        return mapLignePanierToDto(lignePanier);
    }

    // Mettre à jour la quantité d'une ligne du panier
    public LignePanierDto updateQuantiteLignePanier(Long panierId, Long ligneId, Integer quantite) {
        Panier panier = panierRepository.findById(panierId)
                .orElseThrow(() -> new RuntimeException("Panier non trouvé"));

        LignePanier lignePanier = panier.getLignes().stream()
                .filter(l -> l.getId().equals(ligneId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Ligne de panier non trouvée"));

        Product product = lignePanier.getProduct();
        lignePanier.setQuantite(quantite);
        lignePanier.setSousTotal(product.getPrix() * quantite);

        panierRepository.save(panier);

        return mapLignePanierToDto(lignePanier);
    }

    // Mettre à jour le prix unitaire d'une ligne
    public LignePanierDto updatePrixUnitaireLignePanier(Long panierId, Long ligneId, Double prixUnitaire) {
        Panier panier = panierRepository.findById(panierId)
                .orElseThrow(() -> new RuntimeException("Panier non trouvé"));

        LignePanier lignePanier = panier.getLignes().stream()
                .filter(l -> l.getId().equals(ligneId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Ligne de panier non trouvée"));

        lignePanier.setPrixUnitaire(prixUnitaire);
        lignePanier.setSousTotal(prixUnitaire * lignePanier.getQuantite());

        panierRepository.save(panier);

        return mapLignePanierToDto(lignePanier);
    }

    // Récupérer toutes les lignes d'un panier
    public List<LignePanierDto> getAllLignesFromPanier(Long panierId) {
        Panier panier = panierRepository.findById(panierId)
                .orElseThrow(() -> new RuntimeException("Panier non trouvé"));

        return panier.getLignes().stream()
                .map(this::mapLignePanierToDto)
                .toList();
    }

    // Récupérer une ligne de panier spécifique
    public LignePanierDto getLigneFromPanier(Long panierId, Long ligneId) {
        Panier panier = panierRepository.findById(panierId)
                .orElseThrow(() -> new RuntimeException("Panier non trouvé"));

        LignePanier lignePanier = panier.getLignes().stream()
                .filter(l -> l.getId().equals(ligneId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Ligne de panier non trouvée"));

        return mapLignePanierToDto(lignePanier);
    }

    // Méthode pour convertir une LignePanier en LignePanierDto
    private LignePanierDto mapLignePanierToDto(LignePanier lignePanier) {
        if (lignePanier == null) return null;

        LignePanierDto lignePanierDto = new LignePanierDto();
        lignePanierDto.setId(lignePanier.getId());
        lignePanierDto.setProductId(lignePanier.getProduct().getId());
        lignePanierDto.setQuantite(lignePanier.getQuantite());
        lignePanierDto.setPrixUnitaire(lignePanier.getPrixUnitaire());
        lignePanierDto.setSousTotal(lignePanier.getSousTotal());

        return lignePanierDto;
    }
}
