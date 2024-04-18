package br.com.codeup.payment.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClientSaveDTO {

    private static final String BLANK_MESSAGE = "não pode ser nulo, vazio ou em branco.";
    private static final String NOT_NULL_MESSAGE = "Não pode ser nulo.";

    @NotBlank(message = BLANK_MESSAGE)
    private String name;

    @NotBlank(message = BLANK_MESSAGE)
    @Email(message = "é inválido.")
    private String email;

    @NotNull(message = NOT_NULL_MESSAGE)
    private Long documentNumber;

    @NotBlank(message = BLANK_MESSAGE)
    private String password;

    @NotNull(message = NOT_NULL_MESSAGE)
    private BigDecimal balance;

    @NotNull(message = NOT_NULL_MESSAGE)
    private String clientType;

}