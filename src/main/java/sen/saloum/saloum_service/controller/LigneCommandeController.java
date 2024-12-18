package sen.saloum.saloum_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sen.saloum.saloum_service.models.dto.LigneCommandeDto;
import sen.saloum.saloum_service.service.LigneCommandeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ligne-commandes")
@RequiredArgsConstructor
public class LigneCommandeController {

    private final LigneCommandeService ligneCommandeService;

    /**
     * Retrieve all LigneCommande entries.
     *
     * @return a list of LigneCommandeDto
     */
    @GetMapping
    public ResponseEntity<List<LigneCommandeDto>> getAllLigneCommandes() {
        List<LigneCommandeDto> ligneCommandes = ligneCommandeService.getAllLignes();
        return ResponseEntity.ok(ligneCommandes);
    }

    /**
     * Retrieve a specific LigneCommande by ID.
     *
     * @param id the ID of the LigneCommande
     * @return the LigneCommandeDto
     */
    @GetMapping("/{id}")
    public ResponseEntity<LigneCommandeDto> getLigneCommandeById(@PathVariable Long id) {
        return ligneCommandeService.getLigneById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new LigneCommande.
     *
     * @param ligneCommandeDto the LigneCommandeDto to create
     * @return the created LigneCommandeDto
     */
    @PostMapping
    public ResponseEntity<LigneCommandeDto> createLigneCommande(@RequestBody LigneCommandeDto ligneCommandeDto) {
        LigneCommandeDto savedLigneCommande = ligneCommandeService.saveLigne(ligneCommandeDto);
        return ResponseEntity.ok(savedLigneCommande);
    }

    /**
     * Update an existing LigneCommande by ID.
     *
     * @param id               the ID of the LigneCommande to update
     * @param ligneCommandeDto the updated LigneCommandeDto
     * @return the updated LigneCommandeDto
     */
    @PutMapping("/{id}")
    public ResponseEntity<LigneCommandeDto> updateLigneCommande(@PathVariable Long id, @RequestBody LigneCommandeDto ligneCommandeDto) {
        try {
            LigneCommandeDto updatedLigneCommande = ligneCommandeService.updateLigne(id, ligneCommandeDto);
            return ResponseEntity.ok(updatedLigneCommande);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a specific LigneCommande by ID.
     *
     * @param id the ID of the LigneCommande to delete
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLigneCommande(@PathVariable Long id) {
        ligneCommandeService.deleteLigne(id);
        return ResponseEntity.noContent().build();
    }
}
