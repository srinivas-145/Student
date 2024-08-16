package com.example.studentmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/students")
@Validated
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    // 1. Create Student
    @PostMapping
    public ResponseEntity<String> createStudent(@Valid @RequestBody Student student) {
        studentRepository.save(student);
        return ResponseEntity.ok("Student created successfully");
    }

    // 2. Update Student Marks
    @PutMapping("/{id}/marks")
    public ResponseEntity<String> updateMarks(@PathVariable Long id,
                                              @RequestParam @Min(0) @Max(100) Integer marks1,
                                              @RequestParam @Min(0) @Max(100) Integer marks2,
                                              @RequestParam @Min(0) @Max(100) Integer marks3) {
        Optional<Student> studentOpt = studentRepository.findById(id);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            student.setMarks1(marks1);
            student.setMarks2(marks2);
            student.setMarks3(marks3);
            studentRepository.save(student);
            return ResponseEntity.ok("Student marks updated successfully");
        } else {
            return ResponseEntity.status(404).body("Student not found");
        }
    }
}
