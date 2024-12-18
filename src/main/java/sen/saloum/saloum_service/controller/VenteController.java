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
    public ResponseEntity<List<VenteDto>> getAllVentes() {
        List<VenteDto> ventes = venteService.getAllVentes();
        return ResponseEntity.ok(ventes);
    }

    // Retrieve a vente by ID
    @GetMapping("/{id}")
    public ResponseEntity<VenteDto> getVenteById(@PathVariable Long id) {
        Optional<VenteDto> vente = venteService.getVenteById(id);
        return vente.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Create a vente
    @PostMapping
    public ResponseEntity<VenteDto> saveVente(@RequestBody VenteDto venteDto) {
        VenteDto savedVente = venteService.saveVente(venteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVente);
    }

    // Update a vente
    @PutMapping("/{id}")
    public ResponseEntity<VenteDto> updateVente(@PathVariable Long id, @RequestBody VenteDto venteDto) {
        try {
            VenteDto updatedVente = venteService.updateVente(id, venteDto);
            return ResponseEntity.ok(updatedVente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Delete a vente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVente(@PathVariable Long id) {
        try {
            venteService.deleteVente(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
