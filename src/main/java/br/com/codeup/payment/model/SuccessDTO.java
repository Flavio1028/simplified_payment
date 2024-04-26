package br.com.codeup.payment.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessDTO {

    @Schema(description = "Mensagem de retorno da operação", example = "Operação  realizada com sucesso.")
    private String message;

    @Schema(description = "Objeto retornado da operação")
    private Object data;

}