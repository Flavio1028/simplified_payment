package br.com.codeup.payment.util.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessageEnum {

    INTERNAL_ERROR("Erro interno."),
    INVALID_DOCUMENT("Documento inválido."),
    ERROR_VALIDATING_DATA("Erro ao validar os dados."),
    USER_TYPE_ERROR("Tipo de usuário deve ser C (Comum) ou L (Lojista)."),
    UNREGISTERED_CUSTOMER("Cliente não cadastrado."),
    CANNOT_MAKE_TRANSFER("Apenas clientes podem realizar transferências."),
    INSUFFICIENT_BALANCE("Saldo insuficiente para realizar a operação."),
    AUTHORIZED_ERROR("Erro ao chamar autorizador."),
    NOTIFICATION_ERROR("Erro ao enviar notificação.");

    private final String message;

}