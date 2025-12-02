package com.dep.depApp.repository;

import com.dep.depApp.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {
    Optional<Department> findByRoles(String hrAdmin);

    @Query("select e from Department e where e.roles=:roles")
    List<Department>findAllByRoles(String roles, Sort sort);


    Page<Department> findAll(Pageable pageable);

}
