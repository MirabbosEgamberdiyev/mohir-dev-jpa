package fido.uz.mohir_dev_jpa.controller;

import fido.uz.mohir_dev_jpa.dto.ResponseStudentDto;
import fido.uz.mohir_dev_jpa.dto.StudentDto;
import fido.uz.mohir_dev_jpa.entity.Student;
import fido.uz.mohir_dev_jpa.exception.ResponseMessage;
import fido.uz.mohir_dev_jpa.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Operation(
            summary = "Create a new student",
            description = "This endpoint creates a new student with the provided details.",
            tags = {"Student"}
    )
    @ApiResponse(responseCode = "201",
            description = "Student successfully created",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class)
            )
    )
    @PostMapping("/save-student")
    public ResponseEntity<ResponseMessage> createStudent(@Valid @RequestBody StudentDto studentDto) {
        try {
            return studentService.saveStudent(studentDto);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error creating student: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @Operation(
//            summary = "Get a student by ID",
//            description = "This endpoint retrieves a student by their ID.",
//            tags = {"Student"}
//    )
//    @ApiResponse(responseCode = "200",
//            description = "Student successfully retrieved",
//            content = @Content(
//                    mediaType = "application/json",
//                    schema = @Schema(implementation = Student.class)
//            )
//    )
//    @ApiResponse(responseCode = "404",
//            description = "Student not found",
//            content = @Content(
//                    mediaType = "application/json",
//                    schema = @Schema(implementation = ResponseMessage.class)
//            )
//    )
//    @GetMapping("/get-by-id/{id}")
//    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
//        try {
//            return studentService.getByIdStudent(id);
//        } catch (Exception e) {
//            return new ResponseEntity<>(new ResponseMessage("Error retrieving student: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @Operation(
            summary = "Get all students",
            description = "This endpoint retrieves all students.",
            tags = {"Student"}
    )
    @ApiResponse(responseCode = "200",
            description = "Students successfully retrieved",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Student.class)
            )
    )
    @GetMapping("/get-all-students")
    public ResponseEntity<?> getAllStudents() {
        try {
            return studentService.getAllStudent();
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error retrieving students: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Update an existing student",
            description = "This endpoint updates the details of an existing student.",
            tags = {"Student"}
    )
    @ApiResponse(responseCode = "200",
            description = "Student successfully updated",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class)
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Student not found",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class)
            )
    )
    @PutMapping("/update-student")
    public ResponseEntity<ResponseMessage> updateStudent(@Valid @RequestBody Student student) {
        try {
            return studentService.updateStudent(student);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error updating student: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Delete a student by ID",
            description = "This endpoint deletes a student by their ID.",
            tags = {"Student"}
    )
    @ApiResponse(responseCode = "200",
            description = "Student successfully deleted",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class)
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Student not found",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class)
            )
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteStudent(@PathVariable Long id) {
        try {
            return studentService.deleteById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error deleting student: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Get student by email",
            description = "This endpoint retrieves a student by their email address.",
            tags = {"Student"}
    )
    @ApiResponse(responseCode = "200",
            description = "Student successfully retrieved",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Student.class)
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Student not found",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class)
            )
    )
    @ApiResponse(responseCode = "500",
            description = "Internal server error",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class)
            )
    )
    @GetMapping("/by-email")
    public ResponseEntity<?> getStudentByEmail(@Valid @RequestParam(value = "email") String email) {
        try {
            return studentService.getStudentByEmail(email);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error retrieving student by email: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(
            summary = "Get student by phone number",
            description = "This endpoint retrieves a student by their phone number.",
            tags = {"Student"}
    )
    @ApiResponse(responseCode = "200",
            description = "Student successfully retrieved",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Student.class)
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Student not found",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class)
            )
    )
    @ApiResponse(responseCode = "500",
            description = "Internal server error",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class)
            )
    )
    @GetMapping("/by-phone")
    public ResponseEntity<?> getStudentByPhoneNumber(@Valid @RequestParam(value = "phoneNumber") String phoneNumber) {
        try {
            return studentService.getStudentByPhoneNumber(phoneNumber);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error retrieving student by phone number: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Get a student by ID",
            description = "This endpoint retrieves a student by their ID.",
            tags = {"Student"}
    )
    @ApiResponse(responseCode = "200",
            description = "Student successfully retrieved",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Student.class)
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Student not found",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class)
            )
    )
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        try {
            Optional<ResponseStudentDto> studentById = studentService.getStudentById(id);

            return ResponseEntity.ok(studentById.get());
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error retrieving student: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
