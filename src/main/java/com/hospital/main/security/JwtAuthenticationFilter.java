package com.hospital.main.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hospital.main.service.impl.CustomUserDetailService;

import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtHelper helper;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    private Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        

               String requestHeader = request.getHeader("Authorization");

               String username=null;

               String token = null;

               if (requestHeader != null && requestHeader.startsWith("Bearer")) {

                 token =  requestHeader.substring(7);

                 try{
                   username = helper.getUsernameFromToken(token);
                   logger.info(username);
                 }catch(IllegalArgumentException ex){

                    logger.info("Illegal argumenet while fetching the usernam");

                 }catch(MalformedJwtException ex){
                    logger.info("Malformed Jwt Exception");
                 }
                 catch(Exception e){
                    e.printStackTrace();
                 }
                
               }

               if(username!= null && SecurityContextHolder.getContext().getAuthentication()==null){

               UserDetails userDetails = customUserDetailService.loadUserByUsername(username);

                    if(helper.validateToken(token, userDetails)){

                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null , userDetails.getAuthorities());

                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }

               }

               filterChain.doFilter(request, response);
    }

}
