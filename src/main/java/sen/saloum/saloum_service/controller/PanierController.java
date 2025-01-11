package sen.saloum.saloum_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sen.saloum.saloum_service.models.dto.PanierDto;
import sen.saloum.saloum_service.service.PanierService;

import java.util.List;

@RestController
@RequestMapping("/api/paniers")
public class PanierController {

    private final PanierService panierService;

    public PanierController(PanierService panierService) {
        this.panierService = panierService;
    }

     // Endpoint to create a panier for a specific user
    @PostMapping("/create/{utilisateurId}")
    public ResponseEntity<PanierDto> createPanier(@PathVariable Long utilisateurId) {
        PanierDto panierDto = panierService.createPanier(utilisateurId);
        return ResponseEntity.ok(panierDto);
    }

    // Endpoint to add a ligne to the panier
    @PostMapping("/{panierId}/add-ligne")
    public ResponseEntity<PanierDto> addLigneToPanier(
            @PathVariable Long panierId,
            @RequestParam Long productId,
            @RequestParam Integer quantite) {
        PanierDto panierDto = panierService.addLigneToPanier(panierId, productId, quantite);
        return ResponseEntity.ok(panierDto);
    }

    // Endpoint to get a panier by ID
    @GetMapping("/{panierId}")
    public ResponseEntity<PanierDto> getPanierById(@PathVariable Long panierId) {
        PanierDto panierDto = panierService.getPanierById(panierId);
        return ResponseEntity.ok(panierDto);
    }

    // Endpoint to remove a ligne from the panier
    @DeleteMapping("/{panierId}/remove-ligne/{ligneId}")
    public ResponseEntity<PanierDto> removeLigneFromPanier(
            @PathVariable Long panierId, @PathVariable Long ligneId) {
        PanierDto panierDto = panierService.removeLigneFromPanier(panierId, ligneId);
        return ResponseEntity.ok(panierDto);
    }

    // Endpoint to clear the panier
    @DeleteMapping("/{panierId}/clear")
    public ResponseEntity<PanierDto> clearPanier(@PathVariable Long panierId) {
        PanierDto panierDto = panierService.clearPanier(panierId);
        return ResponseEntity.ok(panierDto);
    }
}
