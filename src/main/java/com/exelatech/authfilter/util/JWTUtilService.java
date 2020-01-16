package com.exelatech.authfilter.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

import com.exelatech.authfilter.model.SimpleAuthority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class JWTUtilService {

    private static final long SECONDS = 1000;
    private static final long MINUTES = SECONDS * 60;
    private static final long HOURS = MINUTES * 60;
    private static final long DAY = HOURS * 24;
    private static final long MONTH = DAY * 30;
    private static final long YEAR = MONTH * 12;

    private static final String AUTHORITIES = "ath";

    private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS256;
    // ================================================
    // security 
    // ================================================

    @Autowired
    private KeyStore keyStore;
    
    // ================================================
    // creation methods 
    // ================================================
    private JwtBuilder getBuilderWithClaims(UserDetails userDetails) {
        return Jwts.builder()
            .claim(Claims.ISSUER, "Auth Microservice")
            .claim(Claims.SUBJECT, userDetails.getUsername())
            .claim(AUTHORITIES, userDetails.getAuthorities())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 5 * YEAR))
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE);
    }

    public String generateJwt(UserDetails userDetails) {
        return getBuilderWithClaims(userDetails)
            .signWith(signatureAlgorithm, keyStore.getPrivateKey())
            .compact();
    }

    // ================================================
    // extraction methods 
    // ================================================
    private Claims extractAllClaims(String jws) throws ExpiredJwtException, UnsupportedJwtException,
            MalformedJwtException, SignatureException, IllegalArgumentException {

        return Jwts.parser().setSigningKey(keyStore.getPublicKey()).parseClaimsJws(jws).getBody();
    }

    public String extractSubject(String jws) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException,
            SignatureException, IllegalArgumentException {

        return extractAllClaims(jws).getSubject();
    }

    public ArrayList<SimpleAuthority> extractAuthorities(String jws) {
        
        @SuppressWarnings(value = "unchecked")
        //get metoda vraca listu mapa, iako se u claim stavlja lista SimpleAuthority
        //ne moze se konvertovati iz mape u SimpleAuthority
        //pa mora rucno
        ArrayList<LinkedHashMap<String, String>> o = extractAllClaims(jws).get(AUTHORITIES, ArrayList.class);
        ArrayList<SimpleAuthority> authorities = new ArrayList<SimpleAuthority>();

        for (LinkedHashMap<String, String> map: o) {
            String auth = map.get("authority");
            authorities.add(new SimpleAuthority(auth));
        }

        return authorities;
    }

}
