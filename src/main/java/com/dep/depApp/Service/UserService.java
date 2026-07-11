package com.dep.depApp.Service;

import com.dep.depApp.DTO.*;
import com.dep.depApp.entity.User;
import com.dep.depApp.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public ResponseUserDTO create(@Valid RequestUserDTO requestUserDTO) {
        // User user=userRepository.findByEmail(requestUserDTO.getEmail()).orElseThrow(()->new RuntimeException());
        User user=modelMapper.map(requestUserDTO, User.class);
        User saved= userRepository.save(user);

        return modelMapper.map(saved,ResponseUserDTO.class);
    }

    public List<ResponseUserDTO> getall() {

        List<User> users=userRepository.findAll();
        return users.stream()
                .map(u -> modelMapper.map(u,ResponseUserDTO.class)).collect(Collectors.toList());

    }

    public ResponseUserDTO getByID(Long id) {

        User user=userRepository.findById(id).orElseThrow(null);

        return modelMapper.map(user, ResponseUserDTO.class);

    }

    public UserDTO signup(SignUpDTO signUpDTO) {
         Optional<User> user=userRepository.findByEmail(signUpDTO.getEmail());
         if(user.isPresent()){
             throw  new BadCredentialsException("User with email already exists");
         }

         User tocreate=modelMapper.map(signUpDTO,User.class);
         tocreate.setPassword(passwordEncoder.encode(tocreate.getPassword()));
         User savedUser= userRepository.save(tocreate);

         return modelMapper.map(savedUser,UserDTO.class);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email: " + username));
    }

    public User findUserByID(Long userId) {

        return userRepository.findById(userId).orElseThrow(() ->
                new UsernameNotFoundException("User not found with id:"+ userId));
    }
}
