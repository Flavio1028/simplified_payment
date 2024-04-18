package br.com.codeup.payment.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "client")
@ToString
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;

    @Column(name = "document_type", length = 4, nullable = false)
    private String documentType;

    @Column(name = "document_number", length = 14, nullable = false)
    private Long documentNumber;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "client_type", length = 1, nullable = false)
    private String clientType;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @PrePersist
    private void createDate() {
        this.creationDate = LocalDateTime.now();
    }

    @PreUpdate
    private void updateDate() {
        this.updateDate = LocalDateTime.now();
    }

}