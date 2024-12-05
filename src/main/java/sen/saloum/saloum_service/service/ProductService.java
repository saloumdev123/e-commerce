package sen.saloum.saloum_service.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import sen.saloum.saloum_service.domain.Product;
import sen.saloum.saloum_service.models.dto.ProductDto;
import sen.saloum.saloum_service.repos.ProductRepository;
import sen.saloum.saloum_service.service.interfaces.IProduct;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService implements IProduct {

    private final ProductRepository productRepository;
    private final DtoMapper dtoMapper;


    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(dtoMapper::mapToProductDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDto> getProductById(Long id) {
        return productRepository.findById(id)
                .map(dtoMapper::mapToProductDto);
    }

    @Override
    public ProductDto saveProduct(ProductDto productDto) {
        log.debug("Saving ProductDto: {}", productDto);
        Product product = dtoMapper.mapToProductEntity(productDto);
        product.setDateAjout(LocalDateTime.now());
        log.debug("Mapped ProductDto to Product entity: {}", product);

        try {
            Product savedProduct = productRepository.save(product);
            log.info("Successfully saved Product: {}", savedProduct);
            return dtoMapper.mapToProductDto(savedProduct);
        } catch (Exception e) {
            log.error("Failed to save Product: {}", product, e);
            throw e;
        }
    }

    @Transactional
    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("Produit introuvable avec l'ID: " + id);
        }

        Product product = optionalProduct.get();
        product.setNom(productDto.getNom());
        product.setDescription(productDto.getDescription());
        product.setPrix(productDto.getPrix());
        product.setQuantiteEnStock(productDto.getQuantiteEnStock());
        product.setImageUrl(productDto.getImageUrl());
        product.setCategorie(dtoMapper.mapToCategorieEntity(productDto.getCategorie()));

        Product updatedProduct = productRepository.save(product);
        return dtoMapper.mapToProductDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
