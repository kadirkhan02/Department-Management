package com.dep.depApp.controller;

import com.dep.depApp.DTO.LoginDTO;
import com.dep.depApp.DTO.SignUpDTO;
import com.dep.depApp.DTO.TokenDTO;
import com.dep.depApp.DTO.UserDTO;
import com.dep.depApp.Service.UserService;
import com.dep.depApp.Service.loginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class SignUpController {

    @Autowired
    private UserService userService;

    @Autowired
    private loginService loginService;
   @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody SignUpDTO signUpDTO)
   {
       UserDTO userDTO=userService.signup(signUpDTO);

       return ResponseEntity.ok().body(userDTO);
   }

   @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response)
   {
       String token=loginService.login(loginDTO);

       Cookie cookie= new Cookie("token",token);
       cookie.setHttpOnly(true);
       response.addCookie(cookie);
       return  ResponseEntity.ok(new TokenDTO(token));
   }
}
