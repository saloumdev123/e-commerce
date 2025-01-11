package sen.saloum.saloum_service.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sen.saloum.saloum_service.domain.LignePanier;
import sen.saloum.saloum_service.domain.Panier;
import sen.saloum.saloum_service.domain.Product;
import sen.saloum.saloum_service.domain.Utilisateur;
import sen.saloum.saloum_service.models.dto.LignePanierDto;
import sen.saloum.saloum_service.models.dto.PanierDto;
import sen.saloum.saloum_service.repos.PanierRepository;
import sen.saloum.saloum_service.repos.ProductRepository;
import sen.saloum.saloum_service.repos.UtilisateurRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PanierService {

    private final PanierRepository panierRepository;
    private final ProductRepository productRepository;
    private final UtilisateurRepository utilisateurRepository;

    public PanierService(PanierRepository panierRepository, ProductRepository productRepository, UtilisateurRepository utilisateurRepository) {
        this.panierRepository = panierRepository;
        this.productRepository = productRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    // Ajouter un panier pour un utilisateur
    public PanierDto createPanier(Long utilisateurId) {
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Panier panier = new Panier();
        panier.setUtilisateur(utilisateur);
        panier.setLignes(new ArrayList<>());
        Panier savedPanier = panierRepository.save(panier);
        return mapToDto(savedPanier);
    }

    // Ajouter une ligne au panier
    public PanierDto addLigneToPanier(Long panierId, Long productId, Integer quantite) {
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

        Panier updatedPanier = panierRepository.save(panier);
        return mapToDto(updatedPanier);
    }

    // Récupérer un panier par ID
    public PanierDto getPanierById(Long panierId) {
        Panier panier = panierRepository.findById(panierId)
                .orElseThrow(() -> new RuntimeException("Panier non trouvé"));
        return mapToDto(panier);
    }

    // Supprimer une ligne du panier
    public PanierDto removeLigneFromPanier(Long panierId, Long ligneId) {
        Panier panier = panierRepository.findById(panierId)
                .orElseThrow(() -> new RuntimeException("Panier non trouvé"));

        panier.getLignes().removeIf(ligne -> ligne.getId().equals(ligneId));

        Panier updatedPanier = panierRepository.save(panier);
        return mapToDto(updatedPanier);
    }

    // Vider le panier
    public PanierDto clearPanier(Long panierId) {
        Panier panier = panierRepository.findById(panierId)
                .orElseThrow(() -> new RuntimeException("Panier non trouvé"));

        panier.getLignes().clear();

        Panier updatedPanier = panierRepository.save(panier);
        return mapToDto(updatedPanier);
    }

    // Méthode pour convertir un Panier en PanierDto
    private PanierDto mapToDto(Panier panier) {
        if (panier == null) return null;

        PanierDto panierDto = new PanierDto();
        panierDto.setId(panier.getId());
        panierDto.setUtilisateurId(panier.getUtilisateur().getId());
        panierDto.setLignes(
                panier.getLignes().stream()
                        .map(this::mapLignePanierToDto)
                        .collect(Collectors.toList())
        );

        return panierDto;
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

    // Méthode pour convertir PanierDto en Panier (utile si besoin)
    private Panier mapToEntity(PanierDto panierDto) {
        if (panierDto == null) return null;

        Panier panier = new Panier();
        panier.setId(panierDto.getId());

        Utilisateur utilisateur = utilisateurRepository.findById(panierDto.getUtilisateurId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        panier.setUtilisateur(utilisateur);

        panier.setLignes(
                panierDto.getLignes().stream()
                        .map(this::mapLignePanierToEntity)
                        .collect(Collectors.toList())
        );

        return panier;
    }

    // Méthode pour convertir LignePanierDto en LignePanier
    private LignePanier mapLignePanierToEntity(LignePanierDto lignePanierDto) {
        if (lignePanierDto == null) return null;

        LignePanier lignePanier = new LignePanier();
        lignePanier.setId(lignePanierDto.getId());

        Product product = productRepository.findById(lignePanierDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        lignePanier.setProduct(product);

        lignePanier.setQuantite(lignePanierDto.getQuantite());
        lignePanier.setPrixUnitaire(lignePanierDto.getPrixUnitaire());
        lignePanier.setSousTotal(lignePanierDto.getSousTotal());

        return lignePanier;
    }
}
