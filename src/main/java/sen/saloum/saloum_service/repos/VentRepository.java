package sen.saloum.saloum_service.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sen.saloum.saloum_service.domain.Vente;

public interface VentRepository extends JpaRepository<Vente,Long> {
}
