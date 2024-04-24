package br.com.codeup.payment.model;

import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransferDTO {

    @DecimalMin(value = "0.0", message = "não pode ser negativo.")
    private BigDecimal value;

    private Long payer;

    private Long payee;

}