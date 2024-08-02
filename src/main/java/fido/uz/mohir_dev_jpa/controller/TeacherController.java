package fido.uz.mohir_dev_jpa.controller;

import fido.uz.mohir_dev_jpa.dto.TeacherDto;
import fido.uz.mohir_dev_jpa.entity.Teacher;
import fido.uz.mohir_dev_jpa.exception.ResponseMessage;
import fido.uz.mohir_dev_jpa.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Operation(
            summary = "Create a new teacher",
            description = "This endpoint creates a new teacher with the provided details.",
            tags = {"Teacher"}
    )
    @ApiResponse(responseCode = "201",
            description = "Teacher successfully created",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class)
            )
    )
    @PostMapping("/save")
    public ResponseEntity<ResponseMessage> createTeacher(@Valid @RequestBody TeacherDto teacherDto) {
        try {
            return teacherService.saveTeacher(teacherDto);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error creating teacher: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Get a teacher by ID",
            description = "This endpoint retrieves a teacher by their ID.",
            tags = {"Teacher"}
    )
    @ApiResponse(responseCode = "200",
            description = "Teacher successfully retrieved",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Teacher.class)
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Teacher not found",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class)
            )
    )
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getTeacherById(@PathVariable Long id) {
        try {
            return teacherService.getByIdTeacher(id);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error retrieving teacher by ID: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Get all teachers",
            description = "This endpoint retrieves all teachers.",
            tags = {"Teacher"}
    )
    @ApiResponse(responseCode = "200",
            description = "Teachers successfully retrieved",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Teacher.class)
            )
    )
    @GetMapping("/get-all")
    public ResponseEntity<?> getAllTeachers() {
        try {
            return teacherService.getAllTeachers();
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error retrieving teachers: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Update an existing teacher",
            description = "This endpoint updates the details of an existing teacher.",
            tags = {"Teacher"}
    )
    @ApiResponse(responseCode = "200",
            description = "Teacher successfully updated",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class)
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Teacher not found",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class)
            )
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseMessage> updateTeacher(@Valid @RequestBody Teacher teacher) {
        try {
            return teacherService.updateTeacher(teacher);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error updating teacher: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Delete a teacher by ID",
            description = "This endpoint deletes a teacher by their ID.",
            tags = {"Teacher"}
    )
    @ApiResponse(responseCode = "200",
            description = "Teacher successfully deleted",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class)
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Teacher not found",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class)
            )
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseMessage> deleteTeacher(@PathVariable Long id) {
        try {
            return teacherService.deleteById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error deleting teacher: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Get teacher by email",
            description = "This endpoint retrieves a teacher by their email address.",
            tags = {"Teacher"}
    )
    @ApiResponse(responseCode = "200",
            description = "Teacher successfully retrieved",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Teacher.class)
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Teacher not found",
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
    public ResponseEntity<?> getTeacherByEmail(@RequestParam String email) {
        try {
            return teacherService.getTeacherByEmail(email);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error retrieving teacher by email: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Get teacher by phone number",
            description = "This endpoint retrieves a teacher by their phone number.",
            tags = {"Teacher"}
    )
    @ApiResponse(responseCode = "200",
            description = "Teacher successfully retrieved",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Teacher.class)
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Teacher not found",
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
    public ResponseEntity<?> getTeacherByPhoneNumber(@RequestParam String phoneNumber) {
        try {
            return teacherService.getTeacherByPhoneNumber(phoneNumber);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error retrieving teacher by phone number: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}