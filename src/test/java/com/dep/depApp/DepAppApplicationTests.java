package com.dep.depApp;

import com.dep.depApp.DTO.RequestUserDTO;
import com.dep.depApp.DTO.ResponseUserDTO;
import com.dep.depApp.Service.JwtService;
import com.dep.depApp.Service.UserService;
import com.dep.depApp.entity.Department;
import com.dep.depApp.entity.User;
import com.dep.depApp.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb"
})
@SpringBootTest
class DepAppApplicationTests {

	@Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    UserService userService;
    void testapp()
    {
        Optional<Department> department=departmentRepository.findByRoles("HR_ADMIN");
        department.ifPresent(System.out::println);
    }

    @Autowired
    private JwtService jwtService;
    //@Test

    @Test
    void contextLoads() {
        //('HR', true, CURRENT_TIMESTAMP, 'Handles human resources', 'HRdkk'),
        User user=User.builder()
                .id(1L)
                .email("abdul@gmail.com")
                .password("akdas")
                .build();

        String token=jwtService.generateToken(user);
        System.out.println(token);

        Long id= jwtService.getUserIdFromToken(token);

        System.out.println(id);

    }

    @Test
    void create()
    {
        RequestUserDTO requestUserDTO= new RequestUserDTO(1L,"abdul@gmail.com","Kadir");

        ResponseUserDTO responseUserDTO= userService.create(requestUserDTO);

        System.out.println(responseUserDTO);
        System.out.println("========== Response ==========");
        System.out.println("Id      : " + responseUserDTO.getId());
        System.out.println("Email   : " + responseUserDTO.getEmail());
        System.out.println("==============================");
    }



}
