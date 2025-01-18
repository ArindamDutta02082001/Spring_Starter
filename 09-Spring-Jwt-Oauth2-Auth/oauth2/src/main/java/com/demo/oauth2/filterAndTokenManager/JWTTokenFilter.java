package com.demo.oauth2.filterAndTokenManager;

import com.demo.oauth2.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
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

// this will intercept the request that is incoming and check if it is having a valid bearer token or not
@Component
@RequiredArgsConstructor
public class JWTTokenFilter extends OncePerRequestFilter {

    private final JWTTokenManager jwtService;


    private final UserService userService;

    // to filter the incoming request if it has a valid jwt token ( access token ) or not
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        //Authorization

        String requestHeader = request.getHeader("Authorization"); //Bearer 2352345235sdfrsfgsdfsdf
        logger.info(" Header : "+ requestHeader);


        String username = null;
        String token = null;

        // if user hits the /token api for the first time
        if (requestHeader == null || !requestHeader.startsWith("Bearer ")) {
            logger.info(" User doesn't have any JWT token and is hitting the /token first time with credentials !! ");
            filterChain.doFilter(request, response);            // this routes the user request to the filter chain
            return;
        }

        if (requestHeader.startsWith("Bearer ")) {
            //looking good

            token = requestHeader.substring(7);
            try
            {
                username = this.jwtService.extractUsername(token);
            }
            catch (IllegalArgumentException e) {
                logger.info("Illegal Argument while fetching the username !!");
                e.printStackTrace();
            } catch (ExpiredJwtException e) {
                logger.info("Given jwt token is expired !!");
                e.printStackTrace();
            } catch (MalformedJwtException e) {
                logger.info("Some changed has done in token !! Invalid Token");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();

            }

        } else {
            logger.info("Token is tampered !! ");
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
