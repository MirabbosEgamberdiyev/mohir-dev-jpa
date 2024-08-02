package fido.uz.mohir_dev_jpa.service;

import fido.uz.mohir_dev_jpa.dto.StudentDto;
import fido.uz.mohir_dev_jpa.entity.Student;
import fido.uz.mohir_dev_jpa.exception.ResponseMessage;
import fido.uz.mohir_dev_jpa.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    public ResponseEntity<ResponseMessage> saveStudent(StudentDto studentDto) {
        try {
            Optional<Student> existingStudentOpt = studentRepository.findByEmail(studentDto.getEmail());

            if (existingStudentOpt.isPresent()) {
                return new ResponseEntity<>(new ResponseMessage("Student with email " + studentDto.getEmail() + " already exists.", 400), HttpStatus.BAD_REQUEST);
            }

            Student student = new Student();
            student.setFirst_name(studentDto.getFirst_name());
            student.setLast_name(studentDto.getLast_name());
            student.setEmail(studentDto.getEmail());
            student.setPhoneNumber(studentDto.getPhoneNumber());
            student.setAddress(studentDto.getAddress());
            student.setAge(studentDto.getAge());

            studentRepository.save(student);
            return new ResponseEntity<>(new ResponseMessage("Student was successfully created.", 201), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error creating student: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getByIdStudent(Long id) {
        try {
            Optional<Student> studentOpt = studentRepository.findById(id);

            if (studentOpt.isPresent()) {
                return new ResponseEntity<>(studentOpt.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseMessage("Student with ID " + id + " not found.", 404), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error retrieving student: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ResponseMessage> updateStudent(Student student) {
        try {
            Optional<Student> existingStudentOpt = studentRepository.findById(student.getId());

            if (existingStudentOpt.isPresent()) {
                Student existingStudent = existingStudentOpt.get();

                // Update the student details
                existingStudent.setFirst_name(student.getFirst_name());
                existingStudent.setLast_name(student.getLast_name());
                existingStudent.setEmail(student.getEmail());
                existingStudent.setAddress(student.getAddress());
                existingStudent.setPhoneNumber(student.getPhoneNumber());
                existingStudent.setAge(student.getAge());

                studentRepository.save(existingStudent);

                return new ResponseEntity<>(new ResponseMessage("Student with ID " + student.getId() + " was successfully updated.", 200), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseMessage("Student with ID " + student.getId() + " not found.", 404), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error updating student: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ResponseMessage> deleteById(Long id) {
        try {
            Optional<Student> studentOpt = studentRepository.findById(id);

            if (studentOpt.isPresent()) {
                studentRepository.deleteById(id);
                return new ResponseEntity<>(new ResponseMessage("Student with ID " + id + " was successfully deleted.", 200), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseMessage("Student with ID " + id + " not found.", 404), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error deleting student: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<List<Student>> getAllStudent() {
        List<Student> students = studentRepository.findAll();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // Find a student by email
    public ResponseEntity<?> getStudentByEmail(String email) {
        try {
            Optional<Student> studentOpt = studentRepository.findByEmail(email);
            if (studentOpt.isPresent()) {
                return new ResponseEntity<>(studentOpt.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseMessage("Student with email " + email + " not found.", 404), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error retrieving student by email: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Find a student by phone number
    public ResponseEntity<?> getStudentByPhoneNumber(String phoneNumber) {
        try {
            Optional<Student> studentOpt = studentRepository.findByPhoneNumber(phoneNumber);
            if (studentOpt.isPresent()) {
                return new ResponseEntity<>(studentOpt.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseMessage("Student with phone number " + phoneNumber + " not found.", 404), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error retrieving student by phone number: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
