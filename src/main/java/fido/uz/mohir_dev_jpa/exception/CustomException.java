package fido.uz.mohir_dev_jpa.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class CustomException extends Exception {
    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
