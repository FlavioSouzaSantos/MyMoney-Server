package br.com.mymoney.cadastrationservice.filters;

import br.com.mymoney.crudcommon.utils.JWTUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class JWTAuthenticationFilter implements Filter {
    public static final String BEARER="Bearer";
    public static final String EMPYT = "";

    @Value("${security.jwt.secret:}")
    private String secret;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authorization = ((HttpServletRequest) request).getHeader(HttpHeaders.AUTHORIZATION);
        if(authorization == null ||
                authorization.isBlank() ||
                !authorization.startsWith(BEARER) ||
                JWTUtil.isExpired(authorization.replace(BEARER, EMPYT), secret)){
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }else{
            chain.doFilter(request, response);
        }
    }
}
