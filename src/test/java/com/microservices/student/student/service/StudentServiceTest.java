package com.microservices.student.student.service;

import com.microservices.student.entity.Student;
import com.microservices.student.exception.ResourceNotFoundException;
import com.microservices.student.repository.StudentRepository;
import com.microservices.student.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository repository;

    @InjectMocks
    private StudentService service;

    @Test
    void shouldCreateStudent() {
        Student input = new Student("Bob", "bob@test.com");
        when(repository.save(any(Student.class))).thenReturn(input);

        Student result = service.create(input);

        assertEquals("Bob", result.getName());
    }

    @Test
    void shouldThrowWhenStudentNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getById(99L));
    }
}