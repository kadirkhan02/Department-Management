package com.dep.depApp.DTO;


import com.dep.depApp.enums.Permission;
import com.dep.depApp.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDTO {
    private String email;
    private String password;
    private String name;
    private Set<Role> roles;
    private Set<Permission> permissions;
}
