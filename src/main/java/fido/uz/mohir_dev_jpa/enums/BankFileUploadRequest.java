package fido.uz.mohir_dev_jpa.enums;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Data
public class BankFileUploadRequest {

    @NotNull(message = "{validation.bankFileRequestModel.file.null}")
    private final MultipartFile file;

}
