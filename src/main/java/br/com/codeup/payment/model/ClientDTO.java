package br.com.codeup.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClientDTO extends ClientSaveDTO {

    private Long id;

    private LocalDateTime creationDate;

    private LocalDateTime updateDate;

    @Override
    @JsonIgnore
    public String getPassword() {
        return super.getPassword();
    }

}