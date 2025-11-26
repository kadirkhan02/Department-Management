package com.dep.depApp;

import com.dep.depApp.entity.Department;
import com.dep.depApp.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class DepAppApplicationTests {

	@Autowired
    DepartmentRepository departmentRepository;
    @Test
	void contextLoads() {
	}

    @Test
    void testapp()
    {
        Optional<Department> department=departmentRepository.findByRoles("HR_ADMIN");
        department.ifPresent(System.out::println);
    }
}
