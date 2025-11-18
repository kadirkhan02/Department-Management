package com.dep.depApp.Service;

import com.dep.depApp.DTO.RequestDepartmentDto;
import com.dep.depApp.DTO.ResponseDepartmentDto;
import com.dep.depApp.entity.Department;
import com.dep.depApp.repository.DepartmentRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ReflectionUtils;
import org.modelmapper.ModelMapper;
//import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.util.ReflectionUtils;
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

    public ResponseDepartmentDto updateDepartment(RequestDepartmentDto requestDepartmentDto, long id) {

        checkdepartmentById(id);
        Department d=departmentRepository.findById(id).get();
        Department department=mp.map(requestDepartmentDto,Department.class);
        department.setId(id);
        Department saved=departmentRepository.save(department);
        return mp.map(saved,ResponseDepartmentDto.class);
    }

    public boolean checkdepartmentById(Long id)
    {
        boolean exists=departmentRepository.existsById(id);
        if (!exists) {
            throw new IllegalArgumentException("Department with id " + id + " does not exist");
        }
        return true;
    }

    public boolean deleteDepartment(long id) {
        checkdepartmentById(id);
        departmentRepository.deleteById(id) ;
        return true;
    }
    private Object convertValue(Class<?> targetType, Object value) {

        if (targetType == LocalDateTime.class) {
            return LocalDateTime.parse(value.toString());
        }
        if (targetType == Boolean.class || targetType == boolean.class) {
            return Boolean.parseBoolean(value.toString());
        }
        if (targetType == Long.class || targetType == long.class) {
            return Long.parseLong(value.toString());
        }

        return value;
    }

    public ResponseDepartmentDto updatePartially(Map<String, Object> mpp, long id) {
        checkdepartmentById(id);

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));

        mpp.forEach((field, value) -> {

            Field fieldToBeUpdated = ReflectionUtils.findField(Department.class, field);

            if (fieldToBeUpdated == null) {
                throw new IllegalArgumentException("Invalid field: " + field);
            }

            fieldToBeUpdated.setAccessible(true);

            Object convertedValue = convertValue(fieldToBeUpdated.getType(), value);

            ReflectionUtils.setField(fieldToBeUpdated, department, convertedValue);
        });

        Department saved = departmentRepository.save(department);

        return mp.map(saved, ResponseDepartmentDto.class);
    }
}
