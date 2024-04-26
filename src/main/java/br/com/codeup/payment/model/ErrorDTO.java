package br.com.codeup.payment.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDTO {

    @Schema(description = "Mensagem de retorno da operação", example = "Erro ao executar operação.")
    private String message;

    @Schema(description = "Data da operação", example = "01/04/2024 10:15:36")
    private LocalDateTime data;

    @Schema(description = "Detalhes do erro. (Quando disponível)", example = "[ {  Campo um inválido  } ]")
    private List<String> details;

}