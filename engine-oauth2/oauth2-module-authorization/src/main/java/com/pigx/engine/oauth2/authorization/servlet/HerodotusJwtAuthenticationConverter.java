package com.pigx.engine.oauth2.authorization.servlet;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.oauth2.authorization.converter.HerodotusJwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;


public class HerodotusJwtAuthenticationConverter extends JwtAuthenticationConverter {

    public HerodotusJwtAuthenticationConverter() {
        HerodotusJwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new HerodotusJwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName(SystemConstants.AUTHORITIES);

        this.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
    }
}
