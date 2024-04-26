package br.com.codeup.payment.util.exception;

import br.com.codeup.payment.model.ErrorDTO;
import br.com.codeup.payment.util.enums.ErrorMessageEnum;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ExceptionHandlerControllerTest {

    @InjectMocks
    private ExceptionHandlerController handlerController;

    @Test
    void exceptionTest() {
        ErrorDTO error = this.handlerController.exception(new ValidationErrorException(ErrorMessageEnum.INTERNAL_ERROR));
        assertEquals(ErrorMessageEnum.INTERNAL_ERROR.getMessage(), error.getMessage());
    }

    @Test
    void integrityViolationExceptionTest() {
        ErrorDTO error = this.handlerController.integrityViolationException(new DataIntegrityViolationException("Error", new RuntimeException("Error")));
        assertEquals("Error", error.getMessage());
    }

    @Test
    void constraintViolationExceptionTest() {
        ErrorDTO error = this.handlerController.constraintViolationException(new ConstraintViolationException(new HashSet<>()));
        assertEquals(ErrorMessageEnum.ERROR_VALIDATING_DATA.getMessage(), error.getMessage());
    }

    @Test
    void validationErrorExceptionTest() {
        ErrorDTO error = this.handlerController.validationErrorException(new ValidationErrorException(ErrorMessageEnum.INTERNAL_ERROR));
        assertEquals(ErrorMessageEnum.ERROR_VALIDATING_DATA.getMessage(), error.getMessage());
    }

}