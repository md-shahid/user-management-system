package com.userManagementSystem.service;

import com.userManagementSystem.entity.Employee;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security_key}")
    private String security_key;

    private SecretKey getSecurityKey()

    {
        return Keys.hmacShaKeyFor(security_key.getBytes(StandardCharsets.UTF_8));
    }


    public String generateToken(Employee employee)
    {
        long expiryDateMilli=86400000;
        Date expiryDate = new Date(System.currentTimeMillis() + expiryDateMilli);

        return Jwts.builder()
                .setSubject(employee.getUsername().toString())
                .claim("username",employee.getUsername())
                .claim("Role",employee.getRole())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(getSecurityKey())
                .compact();
    }



    public String getUsernameFromToken(String tatti)

    {
        Claims body = Jwts.parserBuilder()
                .setSigningKey(getSecurityKey())
                .build()
                .parseClaimsJws(tatti)
                .getBody();


        return body.getSubject();

    }







}
