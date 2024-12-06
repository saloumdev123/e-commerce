package sen.saloum.saloum_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sen.saloum.saloum_service.domain.Utilisateur;
import sen.saloum.saloum_service.models.dto.UtilisateurDto;
import sen.saloum.saloum_service.repos.UtilisateurRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    // Retrieve all utilisateurs
    public List<UtilisateurDto> getAllUtilisateurs() {
        return utilisateurRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Retrieve a utilisateur by ID
    public Optional<UtilisateurDto> getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id)
                .map(this::mapToDto);
    }

    // Create or update an utilisateur
    public UtilisateurDto saveUtilisateur(UtilisateurDto utilisateurDto) {

        if (utilisateurDto.getMotDePasse() == null || utilisateurDto.getMotDePasse().isEmpty()) {
            throw new RuntimeException("Mot de passe cannot be null or empty.");
        }
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String hashedPassword = passwordEncoder.encode(utilisateurDto.getMotDePasse());

        Utilisateur utilisateur = mapToEntity(utilisateurDto);
        Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);
        return mapToDto(savedUtilisateur);
    }

    // Delete a utilisateur by ID
    public void deleteUtilisateur(Long id) {
        utilisateurRepository.deleteById(id);
    }


    public UtilisateurDto updateUser(Long id, UtilisateurDto utilisateurDto) {
        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findById(id);

        if (optionalUtilisateur.isEmpty()) {
            throw new IllegalArgumentException("Utilisateur introuvable avec l'ID: " + id);
        }

        Utilisateur utilisateur = optionalUtilisateur.get();

        // Update fields from DTO
        utilisateur.setNom(utilisateurDto.getNom());
        utilisateur.setPrenom(utilisateurDto.getPrenom());
        utilisateur.setEmail(utilisateurDto.getEmail());
        utilisateur.setMotDePasse(utilisateur.getMotDePasse());
        utilisateur.setAdresse(utilisateur.getAdresse());
        utilisateur.setTelephone(utilisateur.getTelephone());

        // Save updated user
        Utilisateur updatedUtilisateur = utilisateurRepository.save(utilisateur);

        // Return updated DTO
        return mapToDto(updatedUtilisateur);
    }

    // Map UtilisateurDto to Utilisateur entity
    public Utilisateur mapToEntity(UtilisateurDto utilisateurDto) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(utilisateurDto.getId());
        utilisateur.setNom(utilisateurDto.getNom());
        utilisateur.setPrenom(utilisateurDto.getPrenom());
        utilisateur.setEmail(utilisateurDto.getEmail());
        utilisateur.setTelephone(utilisateurDto.getTelephone());
        utilisateur.setAdresse(utilisateurDto.getAdresse());
        utilisateur.setMotDePasse(utilisateur.getMotDePasse());
        return utilisateur;
    }

    // Map Utilisateur entity to UtilisateurDto
    public UtilisateurDto mapToDto(Utilisateur utilisateur) {
        return new UtilisateurDto(
                utilisateur.getId(),
                utilisateur.getNom(),
                utilisateur.getPrenom(),
                utilisateur.getTelephone(),
                utilisateur.getAdresse(),
                utilisateur.getEmail(),
                utilisateur.getMotDePasse()
        );
    }
}
