package fido.uz.mohir_dev_jpa.service;

import fido.uz.mohir_dev_jpa.dto.ResponseStudentDto;
import fido.uz.mohir_dev_jpa.dto.StudentDto;
import fido.uz.mohir_dev_jpa.dto.TeacherDto;
import fido.uz.mohir_dev_jpa.dto.UpdateStudent;
import fido.uz.mohir_dev_jpa.entity.Student;
import fido.uz.mohir_dev_jpa.entity.Teacher;
import fido.uz.mohir_dev_jpa.exception.ResponseMessage;
import fido.uz.mohir_dev_jpa.repository.StudentRepository;
import fido.uz.mohir_dev_jpa.repository.TeacherRepository;
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
    private final TeacherRepository teacherRepository;

    public StudentService(StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
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
            student.setTeacher(teacherRepository.getOne(studentDto.getTeacherId()));

            studentRepository.save(student);
            return new ResponseEntity<>(new ResponseMessage("Student was successfully created.", 201), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error creating student: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<ResponseMessage> updateStudent(UpdateStudent student) {
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
                existingStudent.setTeacher(teacherRepository.getOne(student.getTeacherId()));

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

    public Optional<ResponseStudentDto> getStudentById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.map(this::convertToDto);
    }


    // Convert Student entity to ResponseStudentDto
    private ResponseStudentDto convertToDto(Student student) {
        ResponseStudentDto dto = new ResponseStudentDto();
        dto.setId(student.getId());
        dto.setFirst_name(student.getFirst_name());
        dto.setLast_name(student.getLast_name());
        dto.setPhoneNumber(student.getPhoneNumber());
        dto.setEmail(student.getEmail());
        dto.setAge(student.getAge());
        dto.setAddress(student.getAddress());
        // Assuming you have a method to convert Teacher entity to TeacherDto
        dto.setTeacherDto(convertTeacherToDto(student.getTeacher()));
        return dto;
    }

    public ResponseEntity<ResponseStudentDto> getStudentByEmail(String email) {
        try {
            Optional<Student> studentOpt = studentRepository.findByEmail(email);
            if (studentOpt.isPresent()) {
                return new ResponseEntity<>(convertToDto(studentOpt.get()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ResponseStudentDto> getStudentByPhoneNumber(String phoneNumber) {
        try {
            Optional<Student> studentOpt = studentRepository.findByPhoneNumber(phoneNumber);
            if (studentOpt.isPresent()) {
                return new ResponseEntity<>(convertToDto(studentOpt.get()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<ResponseStudentDto>> getAllStudent() {
        try {
            List<Student> students = studentRepository.findAll();
            List<ResponseStudentDto> studentDtos = students.stream()
                    .map(this::convertToDto)
                    .toList();
            return new ResponseEntity<>(studentDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    // Method to convert Teacher entity to TeacherDto
    private TeacherDto convertTeacherToDto(Teacher teacher) {
        if (teacher == null) {
            return null;
        }
        TeacherDto dto = new TeacherDto();
        dto.setFirst_name(teacher.getFirst_name());
        dto.setLast_name(teacher.getLast_name());
        dto.setSubject_name(teacher.getSubject_name());
        dto.setPhoneNumber(teacher.getPhoneNumber());
        dto.setEmail(teacher.getEmail());
        dto.setAge(teacher.getAge());
        dto.setAddress(teacher.getAddress());
        return dto;
    }
}
