package fido.uz.mohir_dev_jpa.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomizedRequestException extends RuntimeException {
    private int code;
    private int httpResponseCode;

    public CustomizedRequestException(String message, int code, int httpResponseCode) {
        super(message);
        this.code = code;
        this.httpResponseCode = httpResponseCode;
    }
}
