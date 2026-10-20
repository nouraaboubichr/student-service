package com.microservices.student.service;

import com.microservices.student.entity.Student;
import com.microservices.student.exception.ResourceNotFoundException;
import com.microservices.student.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> getAll() {
        return repository.findAll();
    }

    public Student getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Étudiant introuvable avec l'id " + id));
    }

    public List<Student> searchByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    public Student create(Student student) {
        return repository.save(student);
    }

    public Student update(Long id, Student data) {
        Student existing = getById(id);
        existing.setName(data.getName());
        existing.setEmail(data.getEmail());
        return repository.save(existing);
    }

    public void delete(Long id) {
        Student existing = getById(id);
        repository.delete(existing);
    }
}