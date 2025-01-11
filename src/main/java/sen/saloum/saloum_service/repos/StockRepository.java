package sen.saloum.saloum_service.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sen.saloum.saloum_service.domain.Stocke;

import java.util.List;

public interface StockRepository extends JpaRepository<Stocke, Long> {
    @Query("SELECT s FROM Stocke s WHERE s.quantiteDisponible < s.seuilAlerte")
    List<Stocke> findByQuantiteDisponibleLessThanSeuilAlerte();

    @Query("SELECT s FROM Stocke s WHERE s.quantiteDisponible < s.seuilAlerte")
    List<Stocke> findLowStock();
}
