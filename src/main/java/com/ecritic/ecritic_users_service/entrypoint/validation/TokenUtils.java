package com.ecritic.ecritic_users_service.entrypoint.validation;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class TokenUtils {

    public static String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser().parseClaimsJwt(token).getBody();
        return claims.get("userId").toString();
    }

    public static String getUserRoleFromToken(String token) {
        Claims claims = Jwts.parser().parseClaimsJwt(token).getBody();
        return claims.get("userRole").toString();
    }
}
