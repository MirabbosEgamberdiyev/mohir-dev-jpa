package fido.uz.mohir_dev_jpa.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import java.io.Serializable;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "fido-student")
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 100, message = "First name must be between 3 and 100 characters")
    @Schema(required = true, description = "Student's first name")
    private String first_name;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 100, message = "Last name must be between 3 and 100 characters")
    @Schema(required = true, description = "Student's last name")
    private String last_name;

    @NotNull
    @NotBlank
    @Size(min = 9, max = 13, message = "Phone number must be between 9 and 13 characters")
    @Schema(required = true, description = "Student's phone number")
    private String phoneNumber;

    @NotNull
    @NotBlank
    @Size(min = 6, max = 200, message = "Email must be between 6 and 200 characters")
    @Email(message = "Please provide a valid email address")
    @Schema(required = true, description = "Student's email address")
    private String email;

    @NotNull
    @Schema(required = true, description = "Student's age")
    private Integer age;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 500, message = "Address must be between 3 and 500 characters")
    @Schema(required = true, description = "Student's address")
    private String address;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;
}
