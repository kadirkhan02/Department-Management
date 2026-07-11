package com.dep.depApp.controller;


import com.dep.depApp.DTO.RequestUserDTO;
import com.dep.depApp.DTO.ResponseUserDTO;
import com.dep.depApp.Service.UserService;
import com.dep.depApp.entity.User;
import com.dep.depApp.exceptions.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("User")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final ModelMapper modelMapper;

    @PostMapping("/post")
    public ResponseEntity<ResponseUserDTO> create(@Valid @RequestBody RequestUserDTO requestUserDTO)
    {

         ResponseUserDTO responseUserDTO=userService.create(requestUserDTO);

         return ResponseEntity.ok().body(responseUserDTO);

    }
    @GetMapping("/getall")
    public ResponseEntity<List<ResponseUserDTO>> getAll()
    {
        List<ResponseUserDTO> responseUserDTOS=userService.getall();
        return ResponseEntity.ok().body(responseUserDTOS);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseUserDTO> getbyid(@PathVariable Long id)
    {
        ResponseUserDTO responseUserDTO=userService.getByID(id);
        return ResponseEntity.ok().body(responseUserDTO);
    }
}
