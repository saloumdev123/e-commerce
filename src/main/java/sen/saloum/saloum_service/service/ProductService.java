package sen.saloum.saloum_service.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import sen.saloum.saloum_service.domain.Categorie;
import sen.saloum.saloum_service.domain.Product;
import sen.saloum.saloum_service.models.dto.CategorieDto;
import sen.saloum.saloum_service.models.dto.ProductDto;
import sen.saloum.saloum_service.models.dto.VenteDto;
import sen.saloum.saloum_service.repos.CategorieRepository;
import sen.saloum.saloum_service.repos.ProductRepository;
import sen.saloum.saloum_service.service.interfaces.IProduct;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
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
    private final CategorieRepository categorieRepository;

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
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
        Categorie categorie = getOrCreateCategorie(productDto);
        Product product = dtoMapper.mapToProductEntity(productDto);
        product.setCategorie(categorie);

        Product savedProduct = productRepository.save(product);
        return dtoMapper.mapToProductDto(savedProduct);
    }

    @Transactional
    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + id));

        existingProduct.setNom(productDto.getNom());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setPrix(productDto.getPrix());
        existingProduct.setQuantiteEnStock(productDto.getQuantiteEnStock());
        existingProduct.setImageUrl(productDto.getImageUrl());
        existingProduct.setDateAjout(OffsetDateTime.now());
        existingProduct.setCategorie(dtoMapper.mapToCategorieEntity(productDto.getCategorie()));

        Product updatedProduct = productRepository.save(existingProduct);
        return dtoMapper.mapToProductDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product not found with ID: " + id);
        }
        productRepository.deleteById(id);
    }

    private Categorie getOrCreateCategorie(ProductDto productDto) {
        if (productDto.getCategorie() == null || productDto.getCategorie().getId() == null) {
            return categorieRepository.findById(1L)
                    .orElseThrow(() -> new RuntimeException("Default Categorie not found"));
        }
        return categorieRepository.findById(productDto.getCategorie().getId())
                .orElseThrow(() -> new RuntimeException("Categorie not found with ID: " + productDto.getCategorie().getId()));
    }
}
