package com.dep.depApp.controller;

import com.dep.depApp.DTO.RequestDepartmentDto;
import com.dep.depApp.DTO.ResponseDepartmentDto;
import com.dep.depApp.Service.DepartmentService;
import com.dep.depApp.entity.Department;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.web.servlet.function.ServerResponse.ok;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    private final DepartmentService departmentService;



    public DepartmentController(DepartmentService departmentService)
    {
        this.departmentService=departmentService;
    }

    @GetMapping()
    public ResponseEntity<List<ResponseDepartmentDto>> getAlldepartment()
    {
        return ResponseEntity.ok(departmentService.getAlldepartment());
    }

    @PostMapping("/addnew")
    public ResponseEntity<ResponseDepartmentDto>add(@RequestBody @Valid RequestDepartmentDto requestDepartmentDto)
    {
        return ResponseEntity.ok(departmentService.add(requestDepartmentDto));
    }
    @PutMapping(path="/{id}")
    public ResponseEntity<ResponseDepartmentDto>updatedepartment(@RequestBody RequestDepartmentDto requestDepartmentDto,@PathVariable long id)
    {
        return ResponseEntity.ok(departmentService.updateDepartment(requestDepartmentDto,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteDepartment(@PathVariable long id)
    {
      boolean deleted=departmentService.deleteDepartment(id);
      if(!deleted)
      {
          return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(true);
    }

    public ResponseEntity<ResponseDepartmentDto>updatePartially(@RequestBody Map<String,Object>mp ,@PathVariable long id)
    {
        ResponseDepartmentDto responseDepartmentDto=departmentService.updatePartially(mp,id);
        return null;
    }
}
