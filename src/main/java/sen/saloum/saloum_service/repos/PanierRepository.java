package sen.saloum.saloum_service.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sen.saloum.saloum_service.domain.Panier;

public interface PanierRepository extends JpaRepository<Panier, Long> {
}
