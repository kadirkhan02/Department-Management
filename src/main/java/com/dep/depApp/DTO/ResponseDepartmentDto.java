package com.dep.depApp.DTO;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseDepartmentDto {


    private long id;
    private String name;
    private boolean isActive;
    private LocalDateTime dateTime;
    private String description;
    private String roles;
}
