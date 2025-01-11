package sen.saloum.saloum_service.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sen.saloum.saloum_service.models.dto.UtilisateurDto;
import sen.saloum.saloum_service.service.UtilisateurService;
import sen.saloum.saloum_service.models.dto.ResponseWrapper;
import sen.saloum.saloum_service.utils.CostumerMessages;

import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
@RequiredArgsConstructor
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    @GetMapping
    public ResponseEntity<List<UtilisateurDto>> getAllUtilisateurs() {
        List<UtilisateurDto> utilisateurs = utilisateurService.getAllUtilisateurs();
        return ResponseEntity.ok(utilisateurs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurDto> getUtilisateurById(@PathVariable Long id) {
        return utilisateurService.getUtilisateurById(id)
                .map(utilisateur -> ResponseEntity.ok(utilisateur))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<UtilisateurDto> createUtilisateur(@RequestBody UtilisateurDto utilisateurDto) {

        if (utilisateurDto.getMotDePasse() == null || utilisateurDto.getMotDePasse().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new UtilisateurDto(null, "Mot de passe est obligatoire"));
        }

        try {
            UtilisateurDto savedUtilisateur = utilisateurService.saveUtilisateur(utilisateurDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUtilisateur);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UtilisateurDto> updateUtilisateur(
            @PathVariable Long id,
            @RequestBody UtilisateurDto utilisateurDto) {
        try {
            UtilisateurDto updatedUtilisateur = utilisateurService.updateUser(id, utilisateurDto);
            return ResponseEntity.ok(updatedUtilisateur);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
        try {
            utilisateurService.deleteUtilisateur(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
