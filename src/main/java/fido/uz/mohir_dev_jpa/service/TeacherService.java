package fido.uz.mohir_dev_jpa.service;

import fido.uz.mohir_dev_jpa.dto.TeacherDto;
import fido.uz.mohir_dev_jpa.entity.Teacher;
import fido.uz.mohir_dev_jpa.exception.ResponseMessage;
import fido.uz.mohir_dev_jpa.repository.TeacherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {


    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public ResponseEntity<ResponseMessage> saveTeacher(TeacherDto teacherDto) {
        try {
            Optional<Teacher> existingTeacherOpt = teacherRepository.findByEmail(teacherDto.getEmail());

            if (existingTeacherOpt.isPresent()) {
                return new ResponseEntity<>(new ResponseMessage("Teacher with email " + teacherDto.getEmail() + " already exists.", 400), HttpStatus.BAD_REQUEST);
            }

            teacherRepository.saveTeacher(teacherDto.getFirst_name(), teacherDto.getLast_name(), teacherDto.getEmail(), teacherDto.getPhoneNumber(), teacherDto.getAddress(), teacherDto.getAge());
            return new ResponseEntity<>(new ResponseMessage("Teacher was successfully created.", 201), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error creating teacher: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getByIdTeacher(Long id) {
        try {
            Optional<Teacher> teacherOpt = teacherRepository.findById(id);

            if (teacherOpt.isPresent()) {
                return new ResponseEntity<>(teacherOpt.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseMessage("Teacher with ID " + id + " not found.", 404), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error retrieving teacher: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ResponseMessage> updateTeacher(Teacher teacher) {
        try {
            Optional<Teacher> existingTeacherOpt = teacherRepository.findById(teacher.getId());

            if (existingTeacherOpt.isPresent()) {
                teacherRepository.updateTeacher(teacher.getId(), teacher.getFirst_name(), teacher.getLast_name(), teacher.getEmail(), teacher.getPhoneNumber(), teacher.getAddress(), teacher.getAge());
                return new ResponseEntity<>(new ResponseMessage("Teacher with ID " + teacher.getId() + " was successfully updated.", 200), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseMessage("Teacher with ID " + teacher.getId() + " not found.", 404), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error updating teacher: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ResponseMessage> deleteById(Long id) {
        try {
            Optional<Teacher> teacherOpt = teacherRepository.findById(id);

            if (teacherOpt.isPresent()) {
                teacherRepository.deleteById(id);
                return new ResponseEntity<>(new ResponseMessage("Teacher with ID " + id + " was successfully deleted.", 200), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseMessage("Teacher with ID " + id + " not found.", 404), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error deleting teacher: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getAllTeachers() {
        try {
            List<Teacher> teachers = teacherRepository.findAllTeachers();
            if (teachers.isEmpty()) {
                return new ResponseEntity<>(new ResponseMessage("No teachers found.", 404), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(teachers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error retrieving all teachers: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<?> getTeacherByEmail(String email) {
        try {
            Optional<Teacher> teacherOpt = teacherRepository.findByEmail(email);
            if (teacherOpt.isPresent()) {
                return new ResponseEntity<>(teacherOpt.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseMessage("Teacher with email " + email + " not found.", 404), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error retrieving teacher by email: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getTeacherByPhoneNumber(String phoneNumber) {
        try {
            Optional<Teacher> teacherOpt = teacherRepository.findByPhoneNumber(phoneNumber);
            if (teacherOpt.isPresent()) {
                return new ResponseEntity<>(teacherOpt.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseMessage("Teacher with phone number " + phoneNumber + " not found.", 404), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error retrieving teacher by phone number: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}