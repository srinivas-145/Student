package com.example.studentmanagement;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.Period;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, message = "First name must be at least 3 characters")
    private String firstName;

    @NotBlank
    @Size(min = 3, message = "Last name must be at least 3 characters")
    private String lastName;

    @NotNull(message = "Date of Birth is mandatory")
    private LocalDate dob;

    @NotBlank(message = "Section is mandatory")
    @Pattern(regexp = "A|B|C", message = "Section must be A, B, or C")
    private String section;

    @NotBlank(message = "Gender is mandatory")
    @Pattern(regexp = "M|F", message = "Gender must be M or F")
    private String gender;

    @Min(value = 0, message = "Marks1 must be between 0 and 100")
    @Max(value = 100, message = "Marks1 must be between 0 and 100")
    private Integer marks1;

    @Min(value = 0, message = "Marks2 must be between 0 and 100")
    @Max(value = 100, message = "Marks2 must be between 0 and 100")
    private Integer marks2;

    @Min(value = 0, message = "Marks3 must be between 0 and 100")
    @Max(value = 100, message = "Marks3 must be between 0 and 100")
    private Integer marks3;

    private Integer total;
    private Double average;
    private String result;

    @PrePersist
    @PreUpdate
    private void calculateTotalAndAverage() {
        if (marks1 != null && marks2 != null && marks3 != null) {
            this.total = marks1 + marks2 + marks3;
            this.average = total / 3.0;

            // Result calculation
            this.result = (marks1 >= 35 && marks2 >= 35 && marks3 >= 35) ? "PASS" : "FAIL";
        }
    }

    // Getters and Setters

    @AssertTrue(message = "Age should be greater than 15 years and less than or equal to 20 years")
    private boolean isAgeValid() {
        if (dob == null) return false;
        int age = Period.between(dob, LocalDate.now()).getYears();
        return age > 15 && age <= 20;
    }
}
