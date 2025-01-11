package sen.saloum.saloum_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sen.saloum.saloum_service.models.dto.CommandeDto;
import sen.saloum.saloum_service.models.dto.ResponseWrapper;
import sen.saloum.saloum_service.service.CommandeService;

import java.util.List;

@RestController
@RequestMapping("/api/commandes")
public class CommandeController {

    private final CommandeService commandeService;

    public CommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    @PostMapping
    public ResponseEntity<CommandeDto> createCommande(@RequestBody CommandeDto commandeDto) {
        CommandeDto createdCommande = commandeService.createCommande(commandeDto);
        return new ResponseEntity<>(createdCommande, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommandeDto> getCommandeById(@PathVariable Long id) {
        CommandeDto commandeDto = commandeService.getCommandeById(id);
        if (commandeDto != null) {
            return new ResponseEntity<>(commandeDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<CommandeDto>> getAllCommandes() {
        List<CommandeDto> commandes = commandeService.getAllCommandes();
        return new ResponseEntity<>(commandes, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommandeDto> updateCommande(
            @PathVariable Long id,
            @RequestBody CommandeDto commandeDto) {
        CommandeDto updatedCommande = commandeService.updateCommande(id, commandeDto);
        if (updatedCommande != null) {
            return new ResponseEntity<>(updatedCommande, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommande(@PathVariable Long id) {
        commandeService.deleteCommande(id);
        return ResponseEntity.noContent().build();  // Status 204 No Content
    }
}
