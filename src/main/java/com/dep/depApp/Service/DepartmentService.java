package com.dep.depApp.Service;

import com.dep.depApp.DTO.RequestDepartmentDto;
import com.dep.depApp.DTO.ResponseDepartmentDto;
import com.dep.depApp.entity.Department;
import com.dep.depApp.repository.DepartmentRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final ModelMapper mp;
    private final DepartmentRepository departmentRepository;
    public List<ResponseDepartmentDto> getAlldepartment() {

       List<Department>d=departmentRepository.findAll();
       return d.stream().
               map(department -> mp.map(department, ResponseDepartmentDto.class))
               .toList();
    }


    public  ResponseDepartmentDto add(RequestDepartmentDto requestDepartmentDto) {
        Department d=mp.map(requestDepartmentDto,Department.class);
        Department saved=departmentRepository.save(d);
        return mp.map(saved,ResponseDepartmentDto.class);
    }
}
