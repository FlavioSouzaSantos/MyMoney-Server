package br.com.mymoney.cadastrationservice.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.java.Log;

import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;

@Log
public class JWTUtil {

    public static boolean isExpired(String token, String secret){
        Optional<Claims> claims = getClaims(token, secret);
        if(claims.isPresent()){
            return false;
        }else{
            Date expiration = claims.map(Claims::getExpiration).get();
            return expiration.before(new Date());
        }
    }

    public static Optional<String> getSubject(String token, String secret){
        Optional<Claims> claims = getClaims(token, secret);
        if(claims.isPresent()){
            return Optional.of(claims.map(Claims::getSubject).get());
        }else{
            return Optional.empty();
        }
    }

    private static Optional<Claims> getClaims(String token, String secret){
        try{
            return Optional.of(Jwts.parserBuilder()
                    .setSigningKey(secret.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody());
        }catch (Exception ex){
            log.log(Level.SEVERE, ex.getMessage(), ex);
            return Optional.empty();
        }
    }
}
