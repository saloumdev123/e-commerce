package sen.saloum.saloum_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sen.saloum.saloum_service.models.dto.AvieDto;

import sen.saloum.saloum_service.models.dto.ResponseWrapper;
import sen.saloum.saloum_service.service.AvieService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/avie")
@RequiredArgsConstructor
public class AvieController {

    private final AvieService avieService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<AvieDto>>> getAllAvies() {
        List<AvieDto> avies = avieService.allAvies();
        ResponseWrapper<List<AvieDto>> response = new ResponseWrapper<>("Avies fetched successfully", avies, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<AvieDto>> getAvieById(@PathVariable Long id) {
        Optional<AvieDto> avieDto = avieService.getById(id);
        if (avieDto.isPresent()) {
            ResponseWrapper<AvieDto> response = new ResponseWrapper<>("Avie fetched successfully", avieDto.get(), true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<AvieDto> response = new ResponseWrapper<>("Avie not found", null, false);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<AvieDto>> createAvie(@RequestBody AvieDto avieDto) {
        try {
            AvieDto savedAvie = avieService.save(avieDto);
            ResponseWrapper<AvieDto> response = new ResponseWrapper<>("Avie created successfully", savedAvie, true);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            ResponseWrapper<AvieDto> response = new ResponseWrapper<>(e.getMessage(), null, false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<AvieDto>> updateAvie(@PathVariable Long id, @RequestBody AvieDto avieDto) {
        try {
            AvieDto updatedAvie = avieService.update(id, avieDto);
            ResponseWrapper<AvieDto> response = new ResponseWrapper<>("Avie updated successfully", updatedAvie, true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ResponseWrapper<AvieDto> response = new ResponseWrapper<>(e.getMessage(), null, false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteAvie(@PathVariable Long id) {
        avieService.delete(id);
        ResponseWrapper<Void> response = new ResponseWrapper<>("Avie deleted successfully", null, true);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
