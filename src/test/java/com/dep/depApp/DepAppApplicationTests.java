package com.dep.depApp;

import com.dep.depApp.Service.JwtService;
import com.dep.depApp.entity.Department;
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
        Department department= Department.builder()
                .id(1L)
                .name("ABDUL")
                .roles("HR")
                .description("yess ")
                .isActive(true)
                .build();

        String token=jwtService.generateToken(department);
        System.out.println(token);

        Long id= jwtService.getUserIdFromToken(token);

        System.out.println(id);

    }


}
