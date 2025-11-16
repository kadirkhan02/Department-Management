package com.dep.depApp.DTO;

import com.dep.depApp.Anotations.CustomPattern;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDepartmentDto {

    @NotBlank
    @Size(min = 3, max = 50, message = "Size should be 3 to 50")
    private String name;


    @AssertTrue(message = "Employee should be active")
    private boolean isActive;

    @PastOrPresent(message = "Date should not be in future")
    private LocalDateTime dateTime;

    @NotBlank
    @Size(min = 3, max = 50, message = "Size should be 3 to 50")
    private String description;

    @CustomPattern(message = "Role must be ADMIN or USER")
    private String roles;

}
