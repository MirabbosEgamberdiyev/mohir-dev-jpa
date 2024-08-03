package fido.uz.mohir_dev_jpa.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseStudent {

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
}
