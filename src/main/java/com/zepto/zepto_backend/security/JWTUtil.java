package com.zepto.zepto_backend.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.zepto.zepto_backend.model.User;
import com.zepto.zepto_backend.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


@Component
public class JWTUtil {

  @Value("${security.secret.password}")
  String secretPassword;
  Long expirationTime = 10000000L;

  @Autowired
  UserService userService;

  public String generateToken(String email, String password){
    String payload = email +":" + password;

    Key key = Keys.hmacShaKeyFor(secretPassword.getBytes());
    
    String JWTToken = Jwts.builder()
      .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
      .setIssuedAt(new Date())
      .signWith(key, SignatureAlgorithm.HS256)
      .setSubject(payload)
      .compact();
      return JWTToken;
  }

  public String decryptToken(String token) {
    SecretKey key = Keys.hmacShaKeyFor(secretPassword.getBytes(StandardCharsets.UTF_8));

    Claims claims = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();

    return claims.getSubject();
}

  public boolean validateToken(String token){
    String payload = this.decryptToken(token);
    String email = payload.split(":")[0];
    String password = payload.split(":")[1];
    User user = userService.isValid(email, password);
    if(user == null){
      return false;
    }
    return true;
  }
}
