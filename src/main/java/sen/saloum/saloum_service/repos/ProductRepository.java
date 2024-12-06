package sen.saloum.saloum_service.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sen.saloum.saloum_service.domain.Product;

import java.util.Optional;

public interface ProductRepository  extends JpaRepository<Product, Long> {
    Optional<Product> findByNom(String nom);
}
