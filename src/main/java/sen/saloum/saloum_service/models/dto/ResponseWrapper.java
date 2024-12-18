package sen.saloum.saloum_service.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWrapper<T> {
    private String message;
    private T data;
    private static boolean success;


}
