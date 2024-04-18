package br.com.codeup.payment.util.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserTypeEnum {

    COMMON("Comum", "C"), STORE("Lojista", "L");

    private final String type;
    private final String code;

}