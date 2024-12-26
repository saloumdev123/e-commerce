package sen.saloum.saloum_service.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockeDto {
    private Long id;
    private Integer quantiteDisponible;
    private Integer seuilAlerte;
    private Long product;
}