package com.ApiGateway.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {


    public static final String SECRET = "mysecretkeyasdfghjklkjhgfdwertyujiklosdfghjklkmnbvcdsdfghjklkbvcdszxcvbn";


    public Claims validateToken(String token) {
    			try {
    				byte[] keyBytes = Decoders.BASE64.decode(SECRET);
    				Key signInKey = Keys.hmacShaKeyFor(keyBytes);
    				return Jwts.parserBuilder()
    						.setSigningKey(signInKey)// ensure secret key is properly encoded
    						.build().
    						parseClaimsJws(token)
    						.getBody();
    			} catch (Exception e) {
					return null;
				}
//       
    }
    
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
		return claimsResolver.apply(claimsJws.getBody());
	}


    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}