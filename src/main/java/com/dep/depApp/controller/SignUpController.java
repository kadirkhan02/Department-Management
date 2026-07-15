package com.dep.depApp.controller;

import com.dep.depApp.DTO.LoginDTO;
import com.dep.depApp.DTO.SignUpDTO;
import com.dep.depApp.DTO.TokenDTO;
import com.dep.depApp.DTO.UserDTO;
import com.dep.depApp.Service.UserService;
import com.dep.depApp.Service.loginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import com.dep.depApp.DTO.TokenDTO;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
public class SignUpController {

    @Autowired
    private UserService userService;

    @Autowired
    private loginService loginService;

    @Value("${deploy.env}")
    private String deployEnv;

   @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody SignUpDTO signUpDTO)
   {
       UserDTO userDTO=userService.signup(signUpDTO);

       return ResponseEntity.ok().body(userDTO);
   }

   @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response)
   {
        TokenDTO tokenDTO =loginService.login(loginDTO);

       Cookie cookie= new Cookie("refreshToken", tokenDTO.getRefreshToken());
       cookie.setHttpOnly(true);
       cookie.setSecure("production".equals(deployEnv));
       response.addCookie(cookie);
       return  ResponseEntity.ok(tokenDTO);
   }

   @PostMapping("/refresh")
    public ResponseEntity<TokenDTO> refresh(HttpServletRequest request)
   {
       String refreshToken=Arrays.stream(request.getCookies())
               .filter(cookie ->"refreshToken".equals(cookie.getName()))
               .findFirst()
               .map(Cookie::getValue)
               .orElseThrow(()->new AuthenticationServiceException("Refresh token not found inside the Cookies"));
       TokenDTO tokenDTO= loginService.refreshToken(refreshToken);

       return ResponseEntity.ok(tokenDTO);
   }
}
