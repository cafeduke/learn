package io.github.cafeduke.dukecart.controller;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/dukecart")
public class CSRFController
{
    // This endpoint must be accessible without full authentication
    @GetMapping("/csrf")
    public CsrfToken getCsrfToken(HttpServletRequest request)
    {
        // Spring Security automatically generates the token and puts it in the request attribute
        // when a request hits the application filter chain.
        return (CsrfToken) request.getAttribute(CsrfToken.class.getName());
    }
    
    @GetMapping("/roles")
    public String getRoles ()
    {
        System.out.println("[RBSESHAD] Inside getRoles");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        Collection<String> roles = null;        
        if (authentication != null && authentication.isAuthenticated()) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            // Extract role names (e.g., "ROLE_ADMIN", "ROLE_USER")
            roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
            System.out.println("[RBSESHAD] User roles: " + roles);
        }        
        return roles.toString();
    }
}
