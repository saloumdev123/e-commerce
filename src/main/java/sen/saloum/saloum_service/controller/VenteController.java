package sen.saloum.saloum_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sen.saloum.saloum_service.models.dto.VenteDto;
import sen.saloum.saloum_service.service.VenteService;
import sen.saloum.saloum_service.models.dto.ResponseWrapper;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ventes")
@RequiredArgsConstructor
public class VenteController {

    private final VenteService venteService;

    // Retrieve all ventes
    @GetMapping
    public ResponseEntity<ResponseWrapper<List<VenteDto>>> getAllVentes() {
        List<VenteDto> ventes = venteService.getAllVentes();
        return ResponseEntity.ok(new ResponseWrapper<>(
                "Liste des ventes récupérée avec succès",
                ventes,
                true
        ));
    }

    // Retrieve a vente by ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<VenteDto>> getVenteById(@PathVariable Long id) {
        Optional<VenteDto> vente = venteService.getVenteById(id);

        if (vente.isPresent()) {
            return ResponseEntity.ok(new ResponseWrapper<>(
                    "Vente trouvée avec succès",
                    vente.get(),
                    true
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
                    "Vente introuvable avec l'ID: " + id,
                    null,
                    false
            ));
        }
    }

    // Create a vente
    @PostMapping
    public ResponseEntity<ResponseWrapper<VenteDto>> saveVente(@RequestBody VenteDto venteDto) {
        VenteDto savedVente = venteService.saveVente(venteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(
                "Vente créée avec succès",
                savedVente,
                true
        ));
    }

    // Update a vente
    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<VenteDto>> updateVente(
            @PathVariable Long id,
            @RequestBody VenteDto venteDto) {

        try {
            VenteDto updatedVente = venteService.updateVente(id, venteDto);
            return ResponseEntity.ok(new ResponseWrapper<>(
                    "Vente mise à jour avec succès",
                    updatedVente,
                    true
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
                    e.getMessage(),
                    null,
                    false
            ));
        }
    }

    // Delete a vente
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteVente(@PathVariable Long id) {
        try {
            venteService.deleteVente(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(
                    "Échec de la suppression de la vente",
                    null,
                    false
            ));
        }
    }
}
