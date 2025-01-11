package sen.saloum.saloum_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sen.saloum.saloum_service.models.dto.LignePanierDto;
import sen.saloum.saloum_service.service.LignePanierService;

import java.util.List;

@RestController
@RequestMapping("/api/ligne-paniers/{panierId}")
public class LignePanierController {

    private final LignePanierService lignePanierService;

    public LignePanierController(LignePanierService lignePanierService) {
        this.lignePanierService = lignePanierService;
    }

    // Endpoint to add a line to the cart
    @PostMapping("/add")
    public ResponseEntity<LignePanierDto> addLigneToPanier(
            @PathVariable Long panierId,
            @RequestParam Long productId,
            @RequestParam Integer quantite) {
        LignePanierDto lignePanierDto = lignePanierService.addLigneToPanier(panierId, productId, quantite);
        return ResponseEntity.ok(lignePanierDto);
    }

    // Endpoint to remove a line from the cart
    @DeleteMapping("/{ligneId}")
    public ResponseEntity<LignePanierDto> removeLigneFromPanier(
            @PathVariable Long panierId, @PathVariable Long ligneId) {
        LignePanierDto lignePanierDto = lignePanierService.removeLigneFromPanier(panierId, ligneId);
        return ResponseEntity.ok(lignePanierDto);
    }

    // Endpoint to update the quantity of a cart line
    @PutMapping("/{ligneId}/quantite")
    public ResponseEntity<LignePanierDto> updateQuantiteLignePanier(
            @PathVariable Long panierId, @PathVariable Long ligneId, @RequestParam Integer quantite) {
        LignePanierDto lignePanierDto = lignePanierService.updateQuantiteLignePanier(panierId, ligneId, quantite);
        return ResponseEntity.ok(lignePanierDto);
    }

    // Endpoint to update the unit price of a cart line
    @PutMapping("/{ligneId}/prix")
    public ResponseEntity<LignePanierDto> updatePrixUnitaireLignePanier(
            @PathVariable Long panierId, @PathVariable Long ligneId, @RequestParam Double prixUnitaire) {
        LignePanierDto lignePanierDto = lignePanierService.updatePrixUnitaireLignePanier(panierId, ligneId, prixUnitaire);
        return ResponseEntity.ok(lignePanierDto);
    }

    // Endpoint to retrieve all lines from a cart
    @GetMapping
    public ResponseEntity<List<LignePanierDto>> getAllLignesFromPanier(@PathVariable Long panierId) {
        List<LignePanierDto> lignes = lignePanierService.getAllLignesFromPanier(panierId);
        return ResponseEntity.ok(lignes);
    }

    // Endpoint to retrieve a specific line from the cart
    @GetMapping("/{ligneId}")
    public ResponseEntity<LignePanierDto> getLigneFromPanier(
            @PathVariable Long panierId, @PathVariable Long ligneId) {
        LignePanierDto lignePanierDto = lignePanierService.getLigneFromPanier(panierId, ligneId);
        return ResponseEntity.ok(lignePanierDto);
    }

}
