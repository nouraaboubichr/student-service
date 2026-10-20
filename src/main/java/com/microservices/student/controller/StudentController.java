package com.microservices.student.controller;

import com.microservices.student.entity.Student;
import com.microservices.student.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@Tag(name = "Students", description = "Gestion des étudiants")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @Operation(summary = "Lister les étudiants",
            description = "Retourne tous les étudiants, ou filtre par nom si le paramètre name est fourni")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping
    public List<Student> getAll(@RequestParam(required = false) String name) {
        if (name != null && !name.isBlank()) {
            return service.searchByName(name);
        }
        return service.getAll();
    }

    @Operation(summary = "Récupérer un étudiant par son id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Étudiant trouvé"),
            @ApiResponse(responseCode = "404", description = "Étudiant introuvable")
    })
    @GetMapping("/{id}")
    public Student getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @Operation(summary = "Créer un nouvel étudiant", description = "Valide l'email avant insertion")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Étudiant créé"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "409", description = "Email déjà utilisé")
    })
    @PostMapping
    public ResponseEntity<Student> create(@Valid @RequestBody Student student) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(student));
    }

    @Operation(summary = "Modifier un étudiant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Étudiant modifié"),
            @ApiResponse(responseCode = "404", description = "Étudiant introuvable")
    })
    @PutMapping("/{id}")
    public Student update(@PathVariable Long id, @Valid @RequestBody Student student) {
        return service.update(id, student);
    }

    @Operation(summary = "Supprimer un étudiant")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Étudiant supprimé"),
            @ApiResponse(responseCode = "404", description = "Étudiant introuvable")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}