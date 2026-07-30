package com.dep.depApp.Service;


import com.dep.depApp.DTO.LoginDTO;
import com.dep.depApp.DTO.TokenDTO;
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

    private  final UserService userService;

    private final SessionService sessionService;

    public TokenDTO login(LoginDTO loginDTO) {

        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword())
        );
        User user= (User) authentication.getPrincipal();

        String accessToken= jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        sessionService.generateNewSession(user,refreshToken);

        return new TokenDTO(user.getId(), accessToken,refreshToken);
    }

    public TokenDTO refreshToken(String refreshToken) {

        Long userId= jwtService.getUserIdFromToken(refreshToken);
        sessionService.validateSession(refreshToken);

        User user=userService.findUserByID(userId);

        String accessToken= jwtService.generateToken(user);

        return new TokenDTO(userId,accessToken,refreshToken);
    }
}
