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
        log.debug("Mapping Product entity to ProductDto: {}", product);
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setNom(product.getNom());
        productDto.setDescription(product.getDescription());
        productDto.setPrix(product.getPrix());
        productDto.setQuantiteEnStock(product.getQuantiteEnStock());
        productDto.setImageUrl(product.getImageUrl());
        productDto.setDateAjout(product.getDateAjout());
        productDto.setCategorie(mapToCategorieDto(product.getCategorie()));
        log.info("Successfully mapped Product entity to ProductDto: {}", productDto);
        return productDto;
    }

    public Product mapToProductEntity(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setNom(productDto.getNom());
        product.setDescription(productDto.getDescription());
        product.setPrix(productDto.getPrix());
        product.setQuantiteEnStock(productDto.getQuantiteEnStock());
        product.setImageUrl(productDto.getImageUrl());
        product.setDateAjout(productDto.getDateAjout());

        if (productDto.getCategorie() != null) {
            Categorie categorie = new Categorie();
            categorie.setId(productDto.getCategorie().getId());
            product.setCategorie(categorie);
        }
        return product;

    }


    // Map Categorie to CategorieDto
    public CategorieDto mapToCategorieDto(Categorie categorie) {
        if (categorie == null) {
            log.error("Attempted to map a null Categorie entity");
            throw new IllegalArgumentException("Categorie cannot be null");
        }

        log.debug("Mapping Categorie entity to CategorieDto: {}", categorie);

        CategorieDto categorieDto = new CategorieDto();
        categorieDto.setId(categorie.getId());
        categorieDto.setNom(categorie.getNom());
        categorieDto.setDescription(categorie.getDescription());

        if (categorie.getProducts() != null) {
            categorieDto.setProducts(
                    categorie.getProducts()
                            .stream()
                            .map(this::mapToProductDto)
                            .collect(Collectors.toList())
            );
        } else {
            log.warn("Categorie with ID {} has a null products list", categorie.getId());
            categorieDto.setProducts(new ArrayList<>());
        }

        log.info("Successfully mapped Categorie entity to CategorieDto: {}", categorieDto);
        return categorieDto;
    }

    // Map CategorieDto to Categorie
    public Categorie mapToCategorieEntity(CategorieDto categorieDto) {
        Categorie categorie = new Categorie();
        categorie.setId(categorieDto.getId());
        categorie.setNom(categorieDto.getNom());
        categorie.setDescription(categorieDto.getDescription());
        return categorie;
    }
}
