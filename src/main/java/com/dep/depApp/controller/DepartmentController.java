package com.dep.depApp.controller;

import com.dep.depApp.DTO.RequestDepartmentDto;
import com.dep.depApp.DTO.ResponseDepartmentDto;
import com.dep.depApp.Service.DepartmentService;
import com.dep.depApp.entity.Department;
import com.dep.depApp.repository.DepartmentRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.web.servlet.function.ServerResponse.ok;

@RestController
@RequestMapping("/api/department")

public class DepartmentController {

    private final DepartmentService departmentService;
    @Autowired
    private DepartmentRepository departmentRepository;

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
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDepartmentDto>updatePartially(@RequestBody Map<String,Object>mp ,@PathVariable long id)
    {
        ResponseDepartmentDto responseDepartmentDto=departmentService.updatePartially(mp,id);
        return new ResponseEntity<>(responseDepartmentDto,HttpStatus.OK);
    }

    @GetMapping("/roles/{role}")
    public ResponseEntity<List<Department>> findByRole(@PathVariable String role, Sort sort) {
        Sort sortby=Sort.by(Sort.Order.asc("name"));
        List<Department> departments = departmentRepository.findAllByRoles(role,sortby);
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/getall")
    public ResponseEntity<?>getall(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pagesize,

            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    )
    {
          Sort sort=direction.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();

        Pageable pageable=PageRequest.of(pageNumber,pagesize,sort);

//        return departmentRepository.findAll(Sort.by(sortBy));
        //return ResponseEntity.ok(departmentRepository.findAll(pageable));

        Page<Department> pageResult = departmentRepository.findAll(pageable);

        return ResponseEntity.ok(pageResult.getContent());
    }
}
