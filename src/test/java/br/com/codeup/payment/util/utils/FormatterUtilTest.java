package br.com.codeup.payment.util.utils;

import br.com.codeup.payment.model.ClientSaveDTO;
import br.com.codeup.payment.service.impl.ClientServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class FormatterUtilTest {

    @Autowired
    private ClientServiceImpl clientService;

    @Test
    void getConstraintViolationsTest() {

        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class,
                () -> clientService.save(new ClientSaveDTO()));
        List<String> erros = FormatterUtil.getConstraintViolations(exception.getConstraintViolations());

        assertFalse(erros.isEmpty());
    }

    @Test
    void validatePasswordSuccessTest() {
        String generatedPassword = FormatterUtil.generatePassword("ABC123");
        Boolean valid = FormatterUtil.validatePassword("ABC123", generatedPassword);
        assertTrue(valid);
    }

}