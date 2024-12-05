package sen.saloum.saloum_service.service.interfaces;

import sen.saloum.saloum_service.models.dto.ProductDto;

import java.util.List;
import java.util.Optional;

public interface IProduct {

    List<ProductDto> getAllProducts();
    Optional<ProductDto> getProductById(Long id);
    ProductDto saveProduct(ProductDto productDto);
    ProductDto updateProduct(Long id, ProductDto productDto);
    void deleteProduct(Long id);
}
