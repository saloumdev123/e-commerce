package sen.saloum.saloum_service.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sen.saloum.saloum_service.domain.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
}
