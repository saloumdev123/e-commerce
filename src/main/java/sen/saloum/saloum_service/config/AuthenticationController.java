package sen.saloum.saloum_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import sen.saloum.saloum_service.domain.Utilisateur;
import sen.saloum.saloum_service.models.dto.UtilisateurDto;
import sen.saloum.saloum_service.models.enums.Roles;
import sen.saloum.saloum_service.repos.UtilisateurRepository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationController(@Lazy AuthenticationManager authenticationManager,
                                    UserDetailsService userDetailsService,
                                    JwtUtil jwtUtil, UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @PostMapping("/register")
    public String registerUser(@RequestBody UtilisateurDto utilisateurDto) {
        Utilisateur newUser = new Utilisateur();
        newUser.setNom(utilisateurDto.getNom());
        newUser.setPrenom(utilisateurDto.getPrenom());
        newUser.setEmail(utilisateurDto.getEmail());
        newUser.setAdresse(utilisateurDto.getAdresse());
        newUser.setTelephone(utilisateurDto.getTelephone());
        newUser.setMotDePasse(passwordEncoder.encode(utilisateurDto.getMotDePasse()));
        newUser.setRole(utilisateurDto.getRole());
        newUser.setDateCreation(OffsetDateTime.now());
        utilisateurRepository.save(newUser);

        // Authenticate the newly registered user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(utilisateurDto.getEmail(),
                        utilisateurDto.getMotDePasse())
        );

        // Generate JWT token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(utilisateurDto.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return jwt;
    }

    @PostMapping("/authenticate")
    public String createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getMotDePasse())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return jwt;
    }
}