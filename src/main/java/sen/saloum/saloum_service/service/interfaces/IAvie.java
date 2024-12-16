package sen.saloum.saloum_service.service.interfaces;

import sen.saloum.saloum_service.models.dto.AvieDto;

import java.util.List;
import java.util.Optional;

public interface IAvie {

    List<AvieDto>  allAvies();
    Optional<AvieDto> getById(Long id);
    AvieDto save(AvieDto avieDto);
    void delete(Long id);
    AvieDto update(Long id, AvieDto avieDto);
}
