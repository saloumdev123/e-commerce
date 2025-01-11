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
    public ResponseEntity<List<LigneVenteDto>> getAllLignesVente() {
        List<LigneVenteDto> lignesVente = ligneVenteService.getAllLignesVente();
        return ResponseEntity.ok(lignesVente);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LigneVenteDto> getLigneVenteById(@PathVariable Long id) {
        try {
            LigneVenteDto ligneVente = ligneVenteService.getLigneVenteById(id);
            return ResponseEntity.ok(ligneVente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<LigneVenteDto> createLigneVente(@RequestBody LigneVenteDto ligneVenteDto) {
        try {
            LigneVenteDto createdLigneVente = ligneVenteService.saveLigneVente(ligneVenteDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLigneVente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LigneVenteDto> updateLigneVente(@PathVariable Long id, @RequestBody LigneVenteDto ligneVenteDto) {
        try {
            LigneVenteDto updatedLigneVente = ligneVenteService.updateLigneVente(id, ligneVenteDto);
            return ResponseEntity.ok(updatedLigneVente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLigneVente(@PathVariable Long id) {
        try {
            ligneVenteService.deleteLigneVente(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
