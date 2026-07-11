package com.dep.depApp.Service;


import com.dep.depApp.DTO.LoginDTO;
import com.dep.depApp.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class loginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    private final JwtService jwtService;
    public String login(LoginDTO loginDTO) {

        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword())
        );
        User user= (User) authentication.getPrincipal();

        return jwtService.generateToken(user);
    }
}
