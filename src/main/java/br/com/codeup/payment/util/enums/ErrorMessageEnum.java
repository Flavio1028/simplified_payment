package br.com.codeup.payment.util.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessageEnum {

    INTERNAL_ERROR("Erro interno."), INVALID_DOCUMENT("Documento inválido."),
    ERROR_VALIDATING_DATA("Erro ao validar os dados."), USER_TYPE_ERROR("Tipo de usuário deve ser C (Comum) ou L (Lojista)."),
    UNREGISTERED_CUSTOMER("Cliente não cadastrado.");

    private final String message;

}