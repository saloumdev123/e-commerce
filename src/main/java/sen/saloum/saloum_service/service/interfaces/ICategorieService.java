package sen.saloum.saloum_service.service.interfaces;


import sen.saloum.saloum_service.models.dto.CategorieDto;
import sen.saloum.saloum_service.service.CategorieService;

import java.util.List;
import java.util.Optional;

public interface ICategorieService {

     List<CategorieDto> getAllCategories();
     Optional<CategorieDto> getCategorieById(Long id);
     CategorieDto saveCategorie(CategorieDto categorieDto);
     CategorieDto updateCategorie(Long id, CategorieDto categorieDto);
     void deleteCategorie(Long id);
}
