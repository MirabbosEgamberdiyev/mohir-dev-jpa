package fido.uz.mohir_dev_jpa.service;

import fido.uz.mohir_dev_jpa.dto.ResponseStudent;
import fido.uz.mohir_dev_jpa.dto.ResponseTeacherDto;
import fido.uz.mohir_dev_jpa.dto.TeacherDto;
import fido.uz.mohir_dev_jpa.dto.UpdateTeacherDto;
import fido.uz.mohir_dev_jpa.entity.Student;
import fido.uz.mohir_dev_jpa.entity.Teacher;
import fido.uz.mohir_dev_jpa.exception.ResponseMessage;
import fido.uz.mohir_dev_jpa.repository.TeacherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeacherService {


    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    // Save a new teacher
    public ResponseEntity<ResponseMessage> saveTeacher(TeacherDto teacherDto) {
        try {
            Optional<Teacher> existingTeacherOpt = teacherRepository.findByEmail(teacherDto.getEmail());

            if (existingTeacherOpt.isPresent()) {
                return new ResponseEntity<>(new ResponseMessage("Teacher with email " + teacherDto.getEmail() + " already exists.", 400), HttpStatus.BAD_REQUEST);
            }

            Teacher teacher = new Teacher();
            teacher.setFirst_name(teacherDto.getFirst_name());
            teacher.setLast_name(teacherDto.getLast_name());
            teacher.setEmail(teacherDto.getEmail());
            teacher.setPhoneNumber(teacherDto.getPhoneNumber());
            teacher.setAddress(teacherDto.getAddress());
            teacher.setAge(teacherDto.getAge());
            teacher.setSubject_name(teacherDto.getSubject_name());

            teacherRepository.save(teacher);

            return new ResponseEntity<>(new ResponseMessage("Teacher was successfully created.", 201), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error creating teacher: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update an existing teacher
    public ResponseEntity<ResponseMessage> updateTeacher(UpdateTeacherDto updateTeacherDto) {
        try {
            Optional<Teacher> optionalTeacher = teacherRepository.findById(updateTeacherDto.getId());
            if (optionalTeacher.isPresent()) {
                Teacher teacher = optionalTeacher.get();
                if (updateTeacherDto.getFirst_name() != null) teacher.setFirst_name(updateTeacherDto.getFirst_name());
                if (updateTeacherDto.getLast_name() != null) teacher.setLast_name(updateTeacherDto.getLast_name());
                if (updateTeacherDto.getSubject_name() != null) teacher.setSubject_name(updateTeacherDto.getSubject_name());
                if (updateTeacherDto.getPhoneNumber() != null) teacher.setPhoneNumber(updateTeacherDto.getPhoneNumber());
                if (updateTeacherDto.getEmail() != null) teacher.setEmail(updateTeacherDto.getEmail());
                if (updateTeacherDto.getAge() != null) teacher.setAge(updateTeacherDto.getAge());
                if (updateTeacherDto.getAddress() != null) teacher.setAddress(updateTeacherDto.getAddress());

                teacherRepository.save(teacher);

                ResponseTeacherDto dto = convertToDto(teacher);
                return new ResponseEntity<>(new ResponseMessage("Teacher updated successfully.", 200), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseMessage("Teacher not found with ID: " + updateTeacherDto.getId(), 404), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error updating teacher: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a teacher by ID
    public ResponseEntity<ResponseMessage> deleteById(Long id) {
        try {
            if (teacherRepository.existsById(id)) {
                teacherRepository.deleteById(id);
                return new ResponseEntity<>(new ResponseMessage("Teacher with ID " + id + " was successfully deleted.", 200), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseMessage("Teacher with ID " + id + " not found.", 404), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error deleting teacher: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Fetch all teachers and convert to DTOs
    public ResponseEntity<List<ResponseTeacherDto>> getAllTeachers() {
        try {
            List<Teacher> teachers = teacherRepository.findAll();
            List<ResponseTeacherDto> teacherDtos = teachers.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(teacherDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Fetch teacher by ID and convert to DTO
    public ResponseEntity<ResponseTeacherDto> getTeacherById(Long id) {
        try {
            Optional<Teacher> teacherOpt = teacherRepository.findById(id);
            return teacherOpt
                    .map(teacher -> new ResponseEntity<>(convertToDto(teacher), HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Fetch teacher by phone number and convert to DTO
    public ResponseEntity<ResponseTeacherDto> getTeacherByPhoneNumber(String phoneNumber) {
        try {
            Optional<Teacher> teacherOpt = teacherRepository.findByPhoneNumber(phoneNumber);
            return teacherOpt
                    .map(teacher -> new ResponseEntity<>(convertToDto(teacher), HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Fetch teacher by email and convert to DTO
    public ResponseEntity<ResponseTeacherDto> getTeacherByEmail(String email) {
        try {
            Optional<Teacher> teacherOpt = teacherRepository.findByEmail(email);
            return teacherOpt
                    .map(teacher -> new ResponseEntity<>(convertToDto(teacher), HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Convert Teacher entity to ResponseTeacherDto
    private ResponseTeacherDto convertToDto(Teacher teacher) {
        ResponseTeacherDto dto = new ResponseTeacherDto();
        dto.setId(teacher.getId()); // Set the ID
        dto.setFirst_name(teacher.getFirst_name());
        dto.setLast_name(teacher.getLast_name());
        dto.setSubject_name(teacher.getSubject_name());
        dto.setPhoneNumber(teacher.getPhoneNumber());
        dto.setEmail(teacher.getEmail());
        dto.setAge(teacher.getAge());
        dto.setAddress(teacher.getAddress());

        List<ResponseStudent> studentDtos = teacher.getStudents().stream()
                .map(this::convertStudentToDto)
                .collect(Collectors.toList());
        dto.setStudents(studentDtos); // Correct method to set students

        return dto;
    }

    // Convert Student entity to ResponseStudent
    private ResponseStudent convertStudentToDto(Student student) {
        ResponseStudent dto = new ResponseStudent();
        dto.setFirst_name(student.getFirst_name());
        dto.setLast_name(student.getLast_name());
        dto.setPhoneNumber(student.getPhoneNumber());
        dto.setEmail(student.getEmail());
        dto.setAge(student.getAge());
        dto.setAddress(student.getAddress());
        return dto;
    }
}