package sen.saloum.saloum_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sen.saloum.saloum_service.models.dto.LigneVenteDto;
import sen.saloum.saloum_service.models.dto.ResponseWrapper;
import sen.saloum.saloum_service.service.LigneVenteService;

import java.util.List;

@RestController
@RequestMapping("/api/ligne-vente")
@RequiredArgsConstructor
public class LigneVenteController {


    private final LigneVenteService ligneVenteService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<LigneVenteDto>>> getAllLignesVente() {
        List<LigneVenteDto> lignesVente = ligneVenteService.getAllLignesVente();
        ResponseWrapper<List<LigneVenteDto>> response = new ResponseWrapper<>(
                "Lignes de vente récupérées avec succès", lignesVente, true);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<LigneVenteDto>> getLigneVenteById(@PathVariable Long id) {
        try {
            LigneVenteDto ligneVente = ligneVenteService.getLigneVenteById(id);
            ResponseWrapper<LigneVenteDto> response = new ResponseWrapper<>(
                    "Ligne de vente récupérée avec succès", ligneVente, true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseWrapper<LigneVenteDto> response = new ResponseWrapper<>(
                    "Ligne de vente non trouvée avec l'ID: " + id, null, false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<LigneVenteDto>> createLigneVente(@RequestBody LigneVenteDto ligneVenteDto) {
        try {
            LigneVenteDto createdLigneVente = ligneVenteService.saveLigneVente(ligneVenteDto);
            ResponseWrapper<LigneVenteDto> response = new ResponseWrapper<>(
                    "Ligne de vente créée avec succès", createdLigneVente, true);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ResponseWrapper<LigneVenteDto> response = new ResponseWrapper<>(
                    "Erreur lors de la création de la ligne de vente", null, false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<LigneVenteDto>> updateLigneVente(
            @PathVariable Long id, @RequestBody LigneVenteDto ligneVenteDto) {
        try {
            LigneVenteDto updatedLigneVente = ligneVenteService.updateLigneVente(id, ligneVenteDto);
            ResponseWrapper<LigneVenteDto> response = new ResponseWrapper<>(
                    "Ligne de vente mise à jour avec succès", updatedLigneVente, true);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ResponseWrapper<LigneVenteDto> response = new ResponseWrapper<>(
                    e.getMessage(), null, false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ResponseWrapper<LigneVenteDto> response = new ResponseWrapper<>(
                    "Erreur interne lors de la mise à jour de la ligne de vente", null, false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteLigneVente(@PathVariable Long id) {
        try {
            ligneVenteService.deleteLigneVente(id);
            ResponseWrapper<Void> response = new ResponseWrapper<>(
                    "Ligne de vente supprimée avec succès", null, true);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (Exception e) {
            ResponseWrapper<Void> response = new ResponseWrapper<>(
                    "Erreur lors de la suppression de la ligne de vente", null, false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
