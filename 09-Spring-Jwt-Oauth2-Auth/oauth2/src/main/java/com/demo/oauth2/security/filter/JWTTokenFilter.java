package com.demo.oauth2.security.filter;

import com.demo.oauth2.security.JWTUtils.JWTTokenManager;
import com.demo.oauth2.service.UserService;
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
public class JWTTokenFilter extends OncePerRequestFilter {

    private final JWTTokenManager jwtTokenManager;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        //Authorization of the

        String requestHeader = request.getHeader("Authorization");
        logger.info(" Header : "+ requestHeader);


        String username = null;
        String token = null;

        // if user hits the /token api for the first time
        if (requestHeader == null || !requestHeader.startsWith("Bearer ")) {
            logger.info(" User doesn't have any JWT token and is hitting the /token first time with credentials !! ");
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
            catch (Exception e) {
                logger.info("Given jwt token is expired !!");
                e.printStackTrace();
            }

        } else {
            logger.info("No JWT token is passed in the header !! ");
        }


        // setting the user detail in the context
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {

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
