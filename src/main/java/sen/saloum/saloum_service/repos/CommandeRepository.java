package sen.saloum.saloum_service.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sen.saloum.saloum_service.domain.Commande;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
}
