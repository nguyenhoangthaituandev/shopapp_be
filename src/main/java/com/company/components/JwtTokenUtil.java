package com.company.components;

import com.company.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    @Value("${jwt.expiration}")
    private int expiration; // save to an environment variable
    @Value("${jwt.secretKey}")
    private String secretKey;

    public String generateToken(User user){
        // properties ~ claims
        Map<String,Object> claims=new HashMap<>();
        claims.put("phoneNumber",user.getPhoneNumber());
        try {
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getPhoneNumber())
                    .setExpiration(new Date(System.currentTimeMillis()+expiration*1000L))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
        }catch(Exception e){
            // You can use Logger, instead sout
            System.out.println("Can not create jwt token, error: "+e.getMessage());
            return null;
        }
    }

    private Key getSignInKey(){
        byte[] bytes= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims=this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // check expiration jwt
    public boolean isTokenExpired(String token){
        Date expirationDate=this.extractClaim(token,Claims::getExpiration);
        return expirationDate.before(new Date());
    }
}
