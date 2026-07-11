package com.dep.depApp.repository;

import com.dep.depApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


     Optional<User> findById(Long aLong);

    Optional<User> findByEmail(String email);

    List<User> findAll();
}
