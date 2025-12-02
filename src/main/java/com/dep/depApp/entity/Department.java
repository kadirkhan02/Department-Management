package com.dep.depApp.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
//@Table(name="department",
//uniqueConstraints = {
//        @UniqueConstraint(name="role_unique",columnNames = {"roles"})
//},
//indexes={
//        @Index(name="roles_index",columnList = "roles")
//        }
//        )
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable=false ,name="Name", length = 20)
    private String name;
    private boolean isActive;
    @CreationTimestamp
    private LocalDateTime dateTime;
    private String description;
    private String roles;

}
