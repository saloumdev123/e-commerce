package sen.saloum.saloum_service.service;

import org.springframework.stereotype.Component;
import sen.saloum.saloum_service.domain.Categorie;
import sen.saloum.saloum_service.domain.Product;
import sen.saloum.saloum_service.models.dto.CategorieDto;
import sen.saloum.saloum_service.models.dto.ProductDto;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class DtoMapper {

    // Mapping Product entity to ProductDto
    public ProductDto mapToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setNom(product.getNom());
        productDto.setDescription(product.getDescription());
        productDto.setPrix(product.getPrix());
        productDto.setImageUrl(product.getImageUrl());
        productDto.setQuantiteEnStock(product.getQuantiteEnStock());

        // Now map only the Categorie ID to the ProductDto
        if (product.getCategorie() != null) {
            productDto.setCategorieId(product.getCategorie().getId()); // Set only the ID
        }
        return productDto;
    }

    // Mapping ProductDto to Product entity
    public Product mapToProductEntity(ProductDto productDto) {
        if (productDto == null) {
            log.error("Cannot map a null ProductDto to Product entity.");
            throw new IllegalArgumentException("ProductDto cannot be null");
        }
        log.debug("Mapping ProductDto to Product entity: {}", productDto);
        Product product = new Product();
        product.setId(productDto.getId());
        product.setNom(productDto.getNom());
        product.setDescription(productDto.getDescription());
        product.setPrix(productDto.getPrix());
        product.setQuantiteEnStock(productDto.getQuantiteEnStock());
        product.setImageUrl(productDto.getImageUrl());
        product.setDateAjout(OffsetDateTime.now());

        // We now map the Categorie using the Categorie ID from ProductDto
        if (productDto.getCategorieId() != null) {
            product.setCategorie(new Categorie()); // Creating an empty Categorie object
            product.getCategorie().setId(productDto.getCategorieId()); // Set the Categorie ID
        }
        log.info("Successfully mapped ProductDto to Product entity: {}", product);
        return product;
    }

    // Mapping Categorie entity to CategorieDto
    public CategorieDto mapToCategorieDto(Categorie categorie) {
        if (categorie == null) {
            log.error("Cannot map a null Categorie entity to CategorieDto.");
            throw new IllegalArgumentException("Categorie cannot be null");
        }
        log.debug("Mapping Categorie entity to CategorieDto: {}", categorie);
        CategorieDto categorieDto = new CategorieDto();
        categorieDto.setId(categorie.getId());
        categorieDto.setNom(categorie.getNom());
        categorieDto.setDescription(categorie.getDescription());

        // If needed, map products to CategorieDto
        if (categorie.getProducts() != null) {
            categorieDto.setProducts(
                    categorie.getProducts().stream()
                            .map(this::mapToProductDto)
                            .collect(Collectors.toList())
            );
        } else {
            categorieDto.setProducts(new ArrayList<>());
        }
        log.info("Successfully mapped Categorie entity to CategorieDto: {}", categorieDto);
        return categorieDto;
    }

    // Mapping Categorie ID to Categorie entity
    public Categorie mapToCategorieEntity(Long categorieId) {
        if (categorieId == null) {
            log.error("Cannot map a null CategorieId to Categorie entity.");
            throw new IllegalArgumentException("CategorieId cannot be null");
        }
        log.debug("Mapping CategorieId to Categorie entity: {}", categorieId);
        Categorie categorie = new Categorie();
        categorie.setId(categorieId); // Set only the Categorie ID
        return categorie;
    }
}
