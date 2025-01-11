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
        return categorieRepository.findAll().stream()
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
        Categorie categorie = dtoMapper.mapToCategorieEntity(categorieDto.getId()) ;

        if (categorieDto.getProducts() != null) {
            List<Product> products = categorieDto.getProducts().stream()
                    .map(productDto -> productRepository.findById(productDto.getId())
                            .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productDto.getId())))
                    .peek(product -> product.setCategorie(categorie))
                    .collect(Collectors.toList());
            categorie.setProducts(products);
        }

        Categorie savedCategorie = categorieRepository.save(categorie);
        return dtoMapper.mapToCategorieDto(savedCategorie);
    }

    @Transactional
    @Override
    public CategorieDto updateCategorie(Long id, CategorieDto categorieDto) {
        Categorie existingCategorie = categorieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categorie not found with ID: " + id));

        existingCategorie.setNom(categorieDto.getNom());
        existingCategorie.setDescription(categorieDto.getDescription());

        Categorie updatedCategorie = categorieRepository.save(existingCategorie);
        return dtoMapper.mapToCategorieDto(updatedCategorie);
    }

    @Override
    public void deleteCategorie(Long id) {
        if (!categorieRepository.existsById(id)) {
            throw new IllegalArgumentException("Categorie not found with ID: " + id);
        }
        categorieRepository.deleteById(id);
    }
}
