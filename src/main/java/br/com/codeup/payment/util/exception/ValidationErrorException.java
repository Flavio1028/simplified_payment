package br.com.codeup.payment.util.exception;

import br.com.codeup.payment.util.enums.ErrorMessageEnum;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
public class ValidationErrorException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public ValidationErrorException(ErrorMessageEnum errorMessage) {
        super(errorMessage.getMessage());
    }

}