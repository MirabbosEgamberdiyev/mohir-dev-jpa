package fido.uz.mohir_dev_jpa.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {

    @NotNull
    @Schema(required = true, description = "Talabaning ismi")
    @Column(nullable = false)
    private String first_name;

    @NotNull
    @Schema(required = true, description = "Talabaning familiyasi")
    @Column(nullable = false)
    private String last_name;

    @NotNull
    @Schema(required = true, description = "Talabaning telfon raqami")
    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @NotNull
    @Email(message = "Iltimos email kiriting")
    @Schema(required = true, description = "Talabaning emaili")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    @Schema(required = true, description = "Talabaning yoshi")
    @Column(nullable = false)
    private Integer age;

    @NotNull
    @NotBlank
    @Schema(required = true, description = "Talabaning manzili")
    @Column(nullable = false)
    private String address;
}
