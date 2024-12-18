package sen.saloum.saloum_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sen.saloum.saloum_service.models.dto.AvieDto;

import sen.saloum.saloum_service.models.dto.ResponseWrapper;
import sen.saloum.saloum_service.service.AvieService;
import sen.saloum.saloum_service.utils.CostumerMessages;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/avie")
@RequiredArgsConstructor
public class AvieController {

    private final AvieService avieService;

    @GetMapping
    public ResponseEntity<List<AvieDto>> getAllAvies() {
        List<AvieDto> avies = avieService.allAvies();
        return new ResponseEntity<>(avies, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvieDto> getAvieById(@PathVariable Long id) {
        Optional<AvieDto> avieDto = avieService.getById(id);
        if (avieDto.isPresent()) {
            return new ResponseEntity<>(avieDto.get(), HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<AvieDto> createAvie(@RequestBody AvieDto avieDto) {
        try {
            AvieDto savedAvie = avieService.save(avieDto);
            return new ResponseEntity<>(savedAvie, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvieDto> updateAvie(@PathVariable Long id, @RequestBody AvieDto avieDto) {
        try {
            AvieDto updatedAvie = avieService.update(id, avieDto);
            return new ResponseEntity<>(updatedAvie, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvie(@PathVariable Long id) {
        avieService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
