package br.com.mymoney.cadastrationservice.filters;

import br.com.mymoney.cadastrationservice.utils.JWTUtil;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class JWTAuthenticationFilter extends BasicAuthenticationFilter {
    private final String BEARER="Bearer";
    private final String KEY_JWT_SECRET="security.jwt.secret";
    private final String EMPYT = "";

    private final Environment environment;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, Environment environment) {
        super(authenticationManager);
        this.environment = environment;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authorization == null || authorization.isEmpty()){
            chain.doFilter(request, response);
        }

        final String token = authorization.replace(BEARER, EMPYT);
        final String secret = environment.getProperty(KEY_JWT_SECRET, EMPYT);

        if(!authorization.startsWith(BEARER) || JWTUtil.isExpired(token, secret)){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        UUID uuid = JWTUtil.getSubject(token, secret).map(p -> UUID.fromString(p)).orElse(null);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(uuid, null);
        authentication.setAuthenticated(true);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
