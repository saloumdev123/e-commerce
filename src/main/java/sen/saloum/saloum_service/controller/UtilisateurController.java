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
    public ResponseEntity<ResponseWrapper<List<UtilisateurDto>>> getAllUtilisateurs() {
        List<UtilisateurDto> utilisateurs = utilisateurService.getAllUtilisateurs();
        ResponseWrapper<List<UtilisateurDto>> response = new ResponseWrapper<>(
                CostumerMessages.FETCH_USERS_SUCCESS, null, false);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<UtilisateurDto>> getUtilisateurById(@PathVariable Long id) {
        return utilisateurService.getUtilisateurById(id)
                .map(utilisateur -> {
                    ResponseWrapper<UtilisateurDto> response = new ResponseWrapper<>(
                            CostumerMessages.FETCH_USER_SUCCESS, null, false);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ResponseWrapper<UtilisateurDto> response = new ResponseWrapper<>(
                            CostumerMessages.USER_NOT_FOUND, null, false);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                });
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<UtilisateurDto>> createUtilisateur(@RequestBody UtilisateurDto utilisateurDto) {
        try {
            UtilisateurDto savedUtilisateur = utilisateurService.saveUtilisateur(utilisateurDto);
            ResponseWrapper<UtilisateurDto> response = new ResponseWrapper<>(
                    CostumerMessages.USER_CREATED, null, false);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseWrapper<UtilisateurDto> response = new ResponseWrapper<>(
                    CostumerMessages. USER_CREATION_FAILED, null, false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteUtilisateur(@PathVariable Long id) {
        try {
            utilisateurService.deleteUtilisateur(id);
            ResponseWrapper<Void> response = new ResponseWrapper<>(
                    CostumerMessages.DELETE_USER, null, false);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            ResponseWrapper<Void> response = new ResponseWrapper<>(
                    CostumerMessages.USER_DELETION_FAILED, null, false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
