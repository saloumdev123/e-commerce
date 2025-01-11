package sen.saloum.saloum_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sen.saloum.saloum_service.models.dto.CategorieDto;
import sen.saloum.saloum_service.service.CategorieService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategorieController {

    private final CategorieService categorieService;

    @GetMapping
    public ResponseEntity<List<CategorieDto>> getAllCategories() {
        List<CategorieDto> categories = categorieService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategorieDto> getCategorieById(@PathVariable Long id) {
        Optional<CategorieDto> categorieDto = categorieService.getCategorieById(id);
        return categorieDto
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<CategorieDto> createCategorie(@RequestBody CategorieDto categorieDto) {
        CategorieDto createdCategorie = categorieService.saveCategorie(categorieDto);
        return new ResponseEntity<>(createdCategorie, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategorieDto> updateCategorie(@PathVariable Long id, @RequestBody CategorieDto categorieDto) {
        try {
            CategorieDto updatedCategorie = categorieService.updateCategorie(id, categorieDto);
            return new ResponseEntity<>(updatedCategorie, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategorie(@PathVariable Long id) {
        categorieService.deleteCategorie(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
