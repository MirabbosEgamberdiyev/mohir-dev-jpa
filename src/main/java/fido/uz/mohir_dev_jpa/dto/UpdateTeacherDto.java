package fido.uz.mohir_dev_jpa.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTeacherDto {

    @NotNull
    private Long id;

    @Size(min = 3, max = 100, message = "First name must be between 3 and 100 characters")
    @Schema(description = "Teacher's first name")
    private String first_name;

    @Size(min = 3, max = 100, message = "Last name must be between 3 and 100 characters")
    @Schema(description = "Teacher's last name")
    private String last_name;

    @Size(min = 3, max = 100, message = "Subject name must be between 3 and 100 characters")
    @Schema(description = "Teacher's subject name")
    private String subject_name;

    @Size(min = 9, max = 13, message = "Phone number must be between 9 and 13 characters")
    @Schema(description = "Teacher's phone number")
    private String phoneNumber;

    @Size(min = 6, max = 200, message = "Email must be between 6 and 200 characters")
    @Email(message = "Please provide a valid email address")
    @Schema(description = "Teacher's email address")
    private String email;

    @Schema(description = "Teacher's age")
    private Integer age;

    @Size(min = 3, max = 500, message = "Address must be between 3 and 500 characters")
    @Schema(description = "Teacher's address")
    private String address;
}

