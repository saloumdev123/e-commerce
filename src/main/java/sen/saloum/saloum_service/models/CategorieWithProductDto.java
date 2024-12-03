package sen.saloum.saloum_service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategorieWithProductDto {

    private Long id;
    private String nom; // Name of the category
    private String description; // Description of the category

    private List<ProductSummaryDto> products;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductSummaryDto {
        private Long id; // Product ID
        private String nom; // Product name
        private String description; // Optional: brief product description
    }

}