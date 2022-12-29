package br.com.mymoney.authserver.services;

import br.com.mymoney.authserver.exceptions.AuthException;
import br.com.mymoney.authserver.models.dtos.AuthDto;
import br.com.mymoney.authserver.models.dtos.TokenDto;
import br.com.mymoney.authserver.models.pojos.CustomUserDetails;
import br.com.mymoney.crudcommon.exceptions.ValidationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

import static br.com.mymoney.authserver.AESUtil.decrypt;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AuthService {

    private final UserService userService;
    private final MessageSource messageSource;
    private final PasswordEncoder passwordEncoder;

    @Value("${security.jwt.secret:}")
    private String secret;

    @Value("${security.jwt.expiration:1800}")
    private Integer expiration;


    @Value("${security.aes.key:}")
    private String aesKey;

    public TokenDto auth(AuthDto authDto) {
        try{
            CustomUserDetails userDetails = (CustomUserDetails) userService.loadUserByUsername(authDto.login());

            String rawPasswordDecrypted = decrypt(authDto.password(), aesKey);
            if(!passwordEncoder.matches(rawPasswordDecrypted, userDetails.getPassword())){
                throw new AuthException(messageSource.getMessage("error.login.or.password.invalid", null, null));
            }
            if(!userDetails.isEnabled() || !userDetails.isAccountNonExpired()){
                throw new AuthException(messageSource.getMessage("error.user.disabled", null, null));
            }
            if(!userDetails.isAccountNonLocked()){
                throw new AuthException(messageSource.getMessage("error.user.locked", null, null));
            }
            if(!userDetails.isCredentialsNonExpired()){
                throw new AuthException(messageSource.getMessage("error.user.password.expired", null, null));
            }

            ZonedDateTime expirationTime = ZonedDateTime.now().plusSeconds(expiration);
            String token = createToken(userDetails, expirationTime);
            return new TokenDto(token, expirationTime);
        }catch (Exception ex){
            if(ex instanceof UsernameNotFoundException)
                throw new ValidationException(ex.getMessage());
            else throw new RuntimeException(ex);
        }
    }

    private String createToken(CustomUserDetails userDetail, ZonedDateTime expirationTime) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(Claims.SUBJECT, userDetail.getUuid());
        claims.put(Claims.ID, userDetail.getUuid());
        claims.put(Claims.EXPIRATION, expirationTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));

        return Jwts.builder().setClaims(claims)
                .setExpiration(Date.from(expirationTime.toInstant()))
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secret.getBytes()))
                .compact();
    }
}
