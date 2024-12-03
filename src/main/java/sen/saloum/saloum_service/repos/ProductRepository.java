package sen.saloum.saloum_service.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sen.saloum.saloum_service.domain.Product;

public interface ProductRepository  extends JpaRepository<Product, Long> {
}
