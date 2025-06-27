package com.auth.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.auth.config.CustomUserDetailsService;
import com.auth.entity.UserCredential;
import com.auth.repository.UserCredentialRepository;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Component
public class JwtService {


    public static final String SECRET = "mysecretkeyasdfghjklkjhgfdwertyujiklosdfghjklkmnbvcdsdfghjklkbvcdszxcvbn";

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    
    @Autowired
    private UserCredentialRepository userCredentialRepository;

    public boolean validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
        return extractClaim(token, Claims::getExpiration).after(new Date());
    }


    public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
    
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
		return claimsResolver.apply(claimsJws.getBody());
	}
    
    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, email);
    }

    public String getRoleFromToken(String token) {
		Claims claims = extractAllClaims(token);
		return claims.get("role", String.class);
	}
    
    private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}
    
    private String createToken(Map<String, Object> claims, String email) {
    	Optional<UserCredential> user= userCredentialRepository.findByEmail(email);
    	claims.put("role", user.get().getRole());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
