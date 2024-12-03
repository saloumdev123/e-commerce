package sen.saloum.saloum_service.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sen.saloum.saloum_service.domain.Stocke;

public interface StockeTRepository extends JpaRepository<Stocke, Long> {
}
