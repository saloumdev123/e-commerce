package sen.saloum.saloum_service.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequest {

    private String email;
    private String motDePasse;
}