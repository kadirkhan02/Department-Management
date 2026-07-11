package com.dep.depApp.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestUserDTO {

    private Long id;


    @Column(nullable = false)
    @Email
    private String email;

    @Column(nullable = false)
    private String password;
}
