package com.harsh.inspireBlog.Jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
public class JwtService {

    @Value("${spring.jwt.secretKey}")
    private String SECRET_KEY;

    @Value("${spring.jwt.expirationTimeMs}")
    private long JWT_TOKEN_VALIDITY;

    // extract user-name from token
    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token, Claims::getSubject);
    }

    // extract expiration date from token
    public Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token, Claims::getExpiration);
    }

    // To extract the claims in generic way
    private <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // To extract all the claims from the token
    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    // To check whether the token is expired or not
    private Boolean isTokenExpired(String token) {
        final Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities());
        return createToken(userDetails.getUsername(), claims);
    }

    // create a token using all the necessary details
    private String createToken(String username, Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY ))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    // To decode the secrete key
    private Key getSignKey() {
        byte[] signkey = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(signkey);
    }

    // To validate the token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String userNameFromToken = getUsernameFromToken(token);
        return (userNameFromToken.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
