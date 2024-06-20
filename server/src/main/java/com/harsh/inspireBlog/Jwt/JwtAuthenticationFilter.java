package com.harsh.inspireBlog.Jwt;

import com.harsh.inspireBlog.Security.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService userDetailsService ;

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String userName = null;

        if(requestHeader != null){
            if (requestHeader.startsWith("Bearer ")){
                jwtToken = requestHeader.substring(7);
                userName = jwtService.getUsernameFromToken(jwtToken);
            }
        } else {
            filterChain.doFilter(request, response);
            return ;
        }

        System.out.println("Jwt Token --> "+jwtToken);
        System.out.println("User Name --> "+userName);

        if(userName != null ){
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            Boolean validateToken = this.jwtService.validateToken(jwtToken, userDetails);
            System.out.println("Validation True or False---> "+validateToken);
            System.out.println(" User Authorities---> "+userDetails.getAuthorities());
            if (validateToken) {
                // set the authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

}
