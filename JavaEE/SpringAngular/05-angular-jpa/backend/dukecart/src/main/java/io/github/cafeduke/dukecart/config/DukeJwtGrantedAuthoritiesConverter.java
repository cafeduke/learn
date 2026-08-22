package io.github.cafeduke.dukecart.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DukeJwtGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>>
{

    private final String authoritiesJsonPath;
    private final String authorityPrefix;

    public DukeJwtGrantedAuthoritiesConverter(String authoritiesJsonPath, String authorityPrefix)
    {
        this.authoritiesJsonPath = authoritiesJsonPath;
        this.authorityPrefix = authorityPrefix;
    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt)
    {
        try
        {
            // Use JsonPath to navigate the nested JSON structure
            List<String> listRole = JsonPath.read(jwt.getClaims(), authoritiesJsonPath);
            
            System.out.println("[RBSESHAD DukeJwtGrantedAuthoritiesConverter] Role=" + listRole);
            
            return listRole.stream()
                .map(role -> new SimpleGrantedAuthority(authorityPrefix + role))
                .collect(Collectors.toList());
        }
        catch (PathNotFoundException e)
        {
            // Handle case where the path doesn't exist
            return Collections.emptyList();
        }
    }
}
