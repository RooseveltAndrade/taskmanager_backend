package com.rooseveltandrade.taskmanager.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 horas

    /**
     * Gera um token JWT para o usuário fornecido.
     *
     * @param username Nome do usuário
     * @return Token JWT
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    /**
     * Valida o token JWT e retorna o nome do usuário se for válido.
     *
     * @param token Token JWT
     * @return Nome do usuário (subject)
     * @throws JwtException Se o token for inválido ou expirado
     */
    public String validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            throw new JwtException("Token expirado", e);
        } catch (UnsupportedJwtException e) {
            throw new JwtException("Token não suportado", e);
        } catch (MalformedJwtException e) {
            throw new JwtException("Token malformado", e);
        } catch (SignatureException e) {
            throw new JwtException("Assinatura inválida", e);
        } catch (IllegalArgumentException e) {
            throw new JwtException("Token vazio ou nulo", e);
        }
    }

    /**
     * Verifica se o token está expirado.
     *
     * @param token Token JWT
     * @return true se o token estiver expirado, false caso contrário
     */
    public boolean isTokenExpired(String token) {
        try {
            Date expiration = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
            return expiration.before(new Date());
        } catch (JwtException e) {
            return true; // Considera o token como expirado em caso de erro
        }
    }
}