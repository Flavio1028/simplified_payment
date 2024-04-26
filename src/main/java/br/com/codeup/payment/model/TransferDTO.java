package br.com.codeup.payment.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransferDTO {

    @Schema(description = "Valor que sera transferido", example = "100.0")
    @DecimalMin(value = "0.0", message = "não pode ser negativo.")
    private BigDecimal value;

    @Schema(description = "Pagador", example = "1")
    private Long payer;

    @Schema(description = "Beneficiário", example = "3")
    private Long payee;

}