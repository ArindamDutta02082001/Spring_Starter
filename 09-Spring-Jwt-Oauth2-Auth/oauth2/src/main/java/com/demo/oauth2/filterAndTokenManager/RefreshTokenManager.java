package com.demo.oauth2.filterAndTokenManager;

import com.demo.oauth2.dto.responseDto;
import com.demo.oauth2.models.DemoUser;
import com.demo.oauth2.models.RefreshToken;
import com.demo.oauth2.repository.RefreshTokenRepository;
import com.demo.oauth2.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class RefreshTokenManager {



    @Autowired
    UserService userService;

    @Autowired
    JWTTokenManager jwtTokenManager;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    private String SECRET_KEY = "secrettttttttttttttttttttttttttttttttt";
    private long REFRESH_TOKEN_VALIDITY = 100*100*100*1296;


    // adding refresh token to our system
    private String createRefreshToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY)).claim("scope" , "REFRESH")
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }
    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createRefreshToken(claims, userDetails.getUsername());
    }

    // generating access token from refresh token
    public Object getAccessTokenFromrefreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshTokenFromHeader;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new responseDto("Bearer","logged out of refresh token : ","refreshTokenFromDb");

        }
        refreshTokenFromHeader = authHeader.substring(7);
        RefreshToken refreshTokenFromDb =  refreshTokenRepository.getRefreshToken(refreshTokenFromHeader);
        username = jwtTokenManager.extractUsername(refreshTokenFromHeader);

        if (username != null) {
            UserDetails user = this.userService.loadUserByUsername(username);

            boolean validRefreshToken = !refreshTokenFromDb.expired && !refreshTokenFromDb.revoked;

            if (validRefreshToken)
            {
                var accessToken = jwtTokenManager.generateToken(user);
                return responseDto.builder().refreshToken(refreshTokenFromHeader).token(accessToken).username(user.getUsername()).build();
            }
            }

        return new responseDto(username,"logged out of refresh token : ",refreshTokenFromDb.getRefreshToken());

    }


    // saving the refresh token in the DB
    public void  saveRefreshTokeninDB(String refreshToken , DemoUser demoUser)
    {
        RefreshToken refreshTokenObject = RefreshToken.builder()
                .refreshToken(refreshToken)
                .user(demoUser)
                .expired(false)
                .expired(false)
                .build();
        refreshTokenRepository.save(refreshTokenObject);
    }


}
