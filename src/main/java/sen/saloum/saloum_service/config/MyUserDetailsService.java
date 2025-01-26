package sen.saloum.saloum_service.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sen.saloum.saloum_service.domain.Utilisateur;
import sen.saloum.saloum_service.repos.UtilisateurRepository;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {


    private final UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(username);
        if (utilisateur == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new MyUserDetails(utilisateur);
    }
}