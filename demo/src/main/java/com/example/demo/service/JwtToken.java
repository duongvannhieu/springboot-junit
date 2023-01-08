package com.example.demo.service;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

public class JwtToken {

    private static final Logger logger = LoggerFactory.getLogger(JwtToken.class);
    private static final long JWT_EXPIRATION = 604800000L;
    private static final String URL_PRIVATE_KEY = "/demo/external/key/jwt-private-key.pem";
    private static final String URL_PUBLIC_KEY = "/demo/external/key/jwt-public-key.pem";

    public static String createJwtSingedHMAC(String userId) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        PrivateKey privateKey = getPrimaryKey();

        Date now = new Date();
        Date jwtExpiration = new Date(now.getTime() + JWT_EXPIRATION);

        String jwtToken = Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(jwtExpiration)
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
        return jwtToken;
    }
    
    private static PrivateKey getPrimaryKey() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        String pathFile = FileSystems.getDefault().getPath("").toAbsolutePath() + URL_PRIVATE_KEY;
        Path path = Paths.get(pathFile);
        String rsaPrivateKey = new String(Files.readAllBytes(path));
        rsaPrivateKey = rsaPrivateKey.replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");
        rsaPrivateKey = rsaPrivateKey.replaceAll("\\s", "");

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(rsaPrivateKey));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }
    
    private static PublicKey getPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        String pathFile = FileSystems.getDefault().getPath("").toAbsolutePath() + URL_PUBLIC_KEY;
        Path path = Paths.get(pathFile);
        String rsaPublicKey = new String(Files.readAllBytes(path));
        rsaPublicKey = rsaPublicKey.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");
        rsaPublicKey = rsaPublicKey.replaceAll("\\s", "");

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(rsaPublicKey));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    public static boolean parseJwt(String jwtString) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        PublicKey publicKey = getPublicKey();

        Claims claims;

        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(jwtString)
                    .getBody();
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid Jwt Token");
        } catch (ExpiredJwtException e) {
            logger.error("Expired Jwt Token");
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported Jwt Token");
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty");
        }
        return false;
    }
    
}
