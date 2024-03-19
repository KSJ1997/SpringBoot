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

    // Контроллер для получения студента по ID
    @GetMapping("/student/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        // Ищем студента по id
        Optional<Student> optionalStudent = students.stream()
            .filter(student -> student.getId().equals(id))
            .findFirst();

        // Если студент найден, возвращаем его
        if (optionalStudent.isPresent()) {
            return ResponseEntity.ok(optionalStudent.get());
        } else {
            // Если студент не найден, возвращаем ошибку 404
            return ResponseEntity.notFound().build();
        }
    }

    @Autowired
    private StudentRepository studentRepository;

    // Получить всех студентов
    @GetMapping("/student")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Поиск студентов по имени
    @GetMapping("/student/search")
    public List<Student> searchStudentsByName(@RequestParam String name) {
        return studentRepository.findByNameContaining(name);
    }

    // Получить всех студентов определенной группы
    @GetMapping("/group/{groupName}/student")
    public List<Student> getStudentsByGroup(@PathVariable String groupName) {
        return studentRepository.findByGroupName(groupName);
    }

    // Метод для инициализации студентов при старте приложения
    @PostConstruct
    public void initStudents() {
        // Создание 5-10 студентов
        for (int i = 1; i <= 10; i++) {
            students.add(new Student((long) i, "Student " + i, "Group " + (i % 3 + 1)));
        }
    }
}
