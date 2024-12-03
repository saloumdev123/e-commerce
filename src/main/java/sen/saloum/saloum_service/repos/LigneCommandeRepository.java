package sen.saloum.saloum_service.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sen.saloum.saloum_service.domain.LigneCommande;

public interface LigneCommandeRepository extends JpaRepository<LigneCommande, Long> {
}
