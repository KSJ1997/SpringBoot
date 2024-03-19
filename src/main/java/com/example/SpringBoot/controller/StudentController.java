package main.java.com.example.SpringBoot.controller;

import org.springframework.web.bind.annotation.*;

import main.java.com.example.SpringBoot.model.Student;
import main.java.com.example.SpringBoot.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class StudentController {

    private final List<Student> students = new ArrayList<>();

    @GetMapping("/student/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {

        Optional<Student> optionalStudent = students.stream()
            .filter(student -> student.getId().equals(id))
            .findFirst();

        if (optionalStudent.isPresent()) {
            return ResponseEntity.ok(optionalStudent.get());
        } else {

            return ResponseEntity.notFound().build();
        }
    }

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/student")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/student/search")
    public List<Student> searchStudentsByName(@RequestParam String name) {
        return studentRepository.findByNameContaining(name);
    }

    @GetMapping("/group/{groupName}/student")
    public List<Student> getStudentsByGroup(@PathVariable String groupName) {
        return studentRepository.findByGroupName(groupName);
    }

    @PostConstruct
    public void initStudents() {

        for (int i = 1; i <= 10; i++) {
            students.add(new Student((long) i, "Student " + i, "Group " + (i % 3 + 1)));
        }
    }
}
