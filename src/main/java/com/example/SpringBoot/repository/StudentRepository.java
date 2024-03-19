package main.java.com.example.SpringBoot.repository;

import main.java.com.example.SpringBoot.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByNameContaining(String name);
    List<Student> findByGroupName(String groupName);
}
