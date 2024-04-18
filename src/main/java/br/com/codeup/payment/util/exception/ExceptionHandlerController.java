package br.com.codeup.payment.util.exception;

import br.com.codeup.payment.model.ErrorDTO;
import br.com.codeup.payment.util.enums.ErrorMessageEnum;
import br.com.codeup.payment.util.utils.FormatterUtil;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {Exception.class})
    public ErrorDTO exception(ValidationErrorException exception) {
        return ErrorDTO.builder()
                .message(ErrorMessageEnum.INTERNAL_ERROR.getMessage())
                .data(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ErrorDTO integrityViolationException(DataIntegrityViolationException exception) {
        return ErrorDTO.builder()
                .message(exception.getCause().getCause().getLocalizedMessage())
                .data(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ErrorDTO integrityViolationException(ConstraintViolationException exception) {
        return ErrorDTO.builder()
                .message(ErrorMessageEnum.ERROR_VALIDATING_DATA.getMessage())
                .data(LocalDateTime.now())
                .details(FormatterUtil.getConstraintViolations(exception.getConstraintViolations()))
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {ValidationErrorException.class})
    public ErrorDTO validationErrorException(ValidationErrorException exception) {
        return ErrorDTO.builder()
                .message(ErrorMessageEnum.ERROR_VALIDATING_DATA.getMessage())
                .data(LocalDateTime.now())
                .details(List.of(exception.getMessage()))
                .build();
    }

}