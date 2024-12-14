package sen.saloum.saloum_service.service;

import org.springframework.stereotype.Component;
import sen.saloum.saloum_service.domain.Categorie;
import sen.saloum.saloum_service.domain.Product;
import sen.saloum.saloum_service.models.dto.CategorieDto;
import sen.saloum.saloum_service.models.dto.ProductDto;

import java.util.ArrayList;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class DtoMapper {

    public ProductDto mapToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setNom(product.getNom());
        productDto.setDescription(product.getDescription());
        productDto.setPrix(product.getPrix());
        productDto.setImageUrl(product.getImageUrl());
        productDto.setQuantiteEnStock(product.getQuantiteEnStock());

        if (product.getCategorie() != null) {
            CategorieDto categorieDto = new CategorieDto();
            categorieDto.setId(product.getCategorie().getId()); // Map only the ID to avoid recursion
            categorieDto.setNom(product.getCategorie().getNom());
            categorieDto.setDescription(product.getDescription());
            productDto.setCategorie(categorieDto);
        }
        return productDto;
    }

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
        product.setDateAjout(productDto.getDateAjout());

        if (productDto.getCategorie() != null) {
            product.setCategorie(mapToCategorieEntity(productDto.getCategorie()));
        }
        log.info("Successfully mapped ProductDto to Product entity: {}", product);
        return product;
    }

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

    public Categorie mapToCategorieEntity(CategorieDto categorieDto) {
        if (categorieDto == null) {
            log.error("Cannot map a null CategorieDto to Categorie entity.");
            throw new IllegalArgumentException("CategorieDto cannot be null");
        }
        log.debug("Mapping CategorieDto to Categorie entity: {}", categorieDto);
        Categorie categorie = new Categorie();
        categorie.setId(categorieDto.getId());
        categorie.setNom(categorieDto.getNom());
        categorie.setDescription(categorieDto.getDescription());
        return categorie;
    }
}
