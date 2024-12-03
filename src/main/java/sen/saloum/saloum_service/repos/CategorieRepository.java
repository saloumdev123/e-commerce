package sen.saloum.saloum_service.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sen.saloum.saloum_service.domain.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
}
