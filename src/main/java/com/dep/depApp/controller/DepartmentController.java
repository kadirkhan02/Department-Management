package com.dep.depApp.controller;

import com.dep.depApp.DTO.RequestDepartmentDto;
import com.dep.depApp.DTO.ResponseDepartmentDto;
import com.dep.depApp.Service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.servlet.function.ServerResponse.ok;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    private final DepartmentService departmentService;



    public DepartmentController(DepartmentService departmentService)
    {
        this.departmentService=departmentService;
    }

    @GetMapping("/getall")
    public ResponseEntity<List<ResponseDepartmentDto>> getAlldepartment()
    {
        return ResponseEntity.ok(departmentService.getAlldepartment());
    }

    @PostMapping("/addnew")
    public ResponseEntity<ResponseDepartmentDto>add(@RequestBody @Valid RequestDepartmentDto requestDepartmentDto)
    {
        return ResponseEntity.ok(departmentService.add(requestDepartmentDto));
    }
}
