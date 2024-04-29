package br.com.codeup.payment.model;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "Nome do cliente", example = "Fulano Teste")
    @NotBlank(message = BLANK_MESSAGE)
    private String name;

    @Schema(description = "E-mail do cliente", example = "teste@teste.com.br")
    @NotBlank(message = BLANK_MESSAGE)
    @Email(message = "é inválido.")
    private String email;

    @Schema(description = "Númedo do documento, podendo ser CPF/CNPJ", example = "12345678911")
    @NotBlank(message = BLANK_MESSAGE)
    private String documentNumber;

    @Schema(description = "Senha do cliente", example = "ABC123")
    @NotBlank(message = BLANK_MESSAGE)
    private String password;

    @Schema(description = "Valor do saldo", example = "1500.0")
    @NotNull(message = NOT_NULL_MESSAGE)
    private BigDecimal balance;

    @Schema(description = "Tipo de Cliente", example = "C", allowableValues = {"C - Cliente", "L - Lojista"})
    @NotNull(message = NOT_NULL_MESSAGE)
    private String clientType;

}