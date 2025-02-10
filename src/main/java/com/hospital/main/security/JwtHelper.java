package com.hospital.main.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtHelper {

    public static final long TOKEN_VALIDITY = 5 * 60 * 60 * 1000;

    public static final String SECRET_KEY = "gcevcfdkbvoifewvewfhbv75t547tyfbiu3rhvc573ygfvb5u3gfy54987yg5uvbybvhivb43uyrfgv4872gyf457gf45ig";

    public String getUsernameFromToken(String token){

        return getClaimFromToken(token , Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token , Function<Claims , T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }


    private Claims getAllClaimsFromToken(String token){
        // return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJwt(token).getBody();
        // return Jwts.parser().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getPayload();

        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token){

        final Date expiration  = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails){
        Map<String , Object> claims = new HashMap<>();
        return doGenerateToken(claims , userDetails.getUsername());
    }

    public String doGenerateToken(Map<String , Object> claims , String subject){

        SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }




}
