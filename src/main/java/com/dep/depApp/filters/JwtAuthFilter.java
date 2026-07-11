package com.dep.depApp.filters;

import com.dep.depApp.Service.JwtService;
import com.dep.depApp.Service.UserService;
import com.dep.depApp.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserService userService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader =request.getHeader("Authorization");
        if(requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer"))
        {
            filterChain.doFilter(request,response);
            return;
        }

        String token=requestTokenHeader.split("Bearer ")[1];

        Long userId =jwtService.getUserIdFromToken(token);

        if(userId !=null & SecurityContextHolder.getContext().getAuthentication()==null)
        {
            User user=userService.findUserByID(userId);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                    new UsernamePasswordAuthenticationToken(user,null,null);
           usernamePasswordAuthenticationToken.setDetails(
                   new WebAuthenticationDetailsSource().buildDetails(request)// to add user ip address and thing like address

           );
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        }
        filterChain.doFilter(request,response);
    }
}
