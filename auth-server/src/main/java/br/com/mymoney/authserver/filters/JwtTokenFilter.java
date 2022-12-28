package br.com.mymoney.authserver.filters;

import br.com.mymoney.authserver.services.UserService;
import br.com.mymoney.crudcommon.utils.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    public static final String BEARER="Bearer";
    public static final String EMPYT = "";

    @Value("${security.jwt.secret:}")
    private String secret;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authorization != null && !authorization.isBlank() &&
            authorization.startsWith(BEARER) && !JWTUtil.isExpired(authorization.replace(BEARER, EMPYT), secret)){
            UUID uuid = JWTUtil.getSubject(authorization.replace(BEARER, EMPYT), secret)
                    .map(UUID::fromString)
                    .orElse(null);
            if(uuid != null){
                UserDetails userDetails = userService.loadUserByUuid(uuid);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}
