package sen.saloum.saloum_service.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sen.saloum.saloum_service.domain.Categorie;
import sen.saloum.saloum_service.domain.Product;
import sen.saloum.saloum_service.models.dto.CategorieDto;
import sen.saloum.saloum_service.models.dto.ProductDto;
import sen.saloum.saloum_service.repos.CategorieRepository;
import sen.saloum.saloum_service.repos.ProductRepository;
import sen.saloum.saloum_service.service.interfaces.ICategorieService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategorieService implements ICategorieService {

    private final CategorieRepository categorieRepository;
    private final ProductRepository productRepository;
    private final DtoMapper dtoMapper;

    @Override
    public List<CategorieDto> getAllCategories() {
        return categorieRepository.findAll()
                .stream()
                .map(dtoMapper::mapToCategorieDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CategorieDto> getCategorieById(Long id) {
        return categorieRepository.findById(id)
                .map(dtoMapper::mapToCategorieDto);
    }


    @Override
    public CategorieDto saveCategorie(CategorieDto categorieDto) {
        // Map DTO to Entity
        Categorie categorie = new Categorie();
        categorie.setNom(categorieDto.getNom());
        categorie.setDescription(categorieDto.getDescription());

        List<Product> products = new ArrayList<>();
        for (ProductDto productDto : categorieDto.getProducts()) {
            // Fetch the existing product from the database
            Product product = productRepository.findById(productDto.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productDto.getId()));

            // Associate the existing product with the category
            product.setCategorie(categorie);
            products.add(product);
        }

        categorie.setProducts(products);

        // Save the category and associated products
        Categorie savedCategorie = categorieRepository.save(categorie);

        // Map saved entity back to DTO
        CategorieDto savedCategorieDto = new CategorieDto();
        savedCategorieDto.setId(savedCategorie.getId());
        savedCategorieDto.setNom(savedCategorie.getNom());
        savedCategorieDto.setDescription(savedCategorie.getDescription());

        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : savedCategorie.getProducts()) {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setNom(product.getNom());
            productDto.setPrix(product.getPrix());
            productDto.setDescription(product.getDescription());
            productDto.setImageUrl(product.getImageUrl());
            productDto.setDateAjout(product.getDateAjout());
            productDto.setQuantiteEnStock(product.getQuantiteEnStock());
            productDtos.add(productDto);
        }
        savedCategorieDto.setProducts(productDtos);

        return savedCategorieDto;
    }




    @Transactional
    @Override
    public CategorieDto updateCategorie(Long id, CategorieDto categorieDto) {
        Optional<Categorie> optionalCategorie = categorieRepository.findById(id);

        if (optionalCategorie.isEmpty()) {
            throw new IllegalArgumentException("Catégorie introuvable avec l'ID: " + id);
        }

        Categorie categorie = optionalCategorie.get();
        categorie.setNom(categorieDto.getNom());
        categorie.setDescription(categorieDto.getDescription());

        Categorie updatedCategorie = categorieRepository.save(categorie);
        return dtoMapper.mapToCategorieDto(updatedCategorie);
    }

    @Override
    public void deleteCategorie(Long id) {
        categorieRepository.deleteById(id);
    }
}
