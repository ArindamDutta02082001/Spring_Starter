package com.example.demospringsecurity.database.security.filters;



import com.example.demospringsecurity.database.security.JWTUtils.JWTTokenManager;
import com.example.demospringsecurity.database.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * this filter will intercept everytime the incoming http request that is incoming and check
 * if it is having a valid bearer token or not by using the jwt services ( inbuilt )
 */

@Component
@RequiredArgsConstructor
public class CustomJWTFilter extends OncePerRequestFilter {

    private final JWTTokenManager jwtTokenManager;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        //Authorization of the sent JWT token via header

        String requestHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;

        // if user hits the /token api for the first time
        if (requestHeader == null || !requestHeader.startsWith("Bearer ")) {
            logger.info(" User didnt sent any JWT token and is hitting the api first time with credentials .PLease register !! ");
            filterChain.doFilter(request, response);            // this routes the user request to the filter chain
            return;
        }

        // extraction of the JWT token from header and again extracting the username from that JWT
        if (requestHeader.startsWith("Bearer ")) {

            token = requestHeader.substring(7);
            try
            {
                username = this.jwtTokenManager.extractUsername(token);
            }
            catch (ExpiredJwtException e)
            {
                logger.info("Given jwt token is expired !!");
                e.printStackTrace();
            }
            catch (Exception e) {
                logger.info("Given jwt token is malformed !!");
                e.printStackTrace();
            }

        } else {
            logger.info("No JWT token is passed in the header !! ");
        }


        // setting the user detail in the context
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            // it will be different for different DB or use cases
            UserDetails userDetails = this.userService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);

        }

        filterChain.doFilter( request, response);
    }
}
