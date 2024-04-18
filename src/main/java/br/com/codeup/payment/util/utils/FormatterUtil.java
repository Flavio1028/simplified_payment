package br.com.codeup.payment.util.utils;

import com.password4j.BcryptFunction;
import com.password4j.Hash;
import com.password4j.Password;
import com.password4j.types.Bcrypt;
import jakarta.validation.ConstraintViolation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FormatterUtil {

    private static final String HASH_SECRET = "d41d8cd98f00b204e9800998ecf8427e";

    private static final BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.B, 12);

    public static List<String> getConstraintViolations(Set<ConstraintViolation<?>> constraintViolations) {
        return constraintViolations.stream()
                .map(violation -> String.format("O campo %s %s", StreamSupport.stream(violation.getPropertyPath().spliterator(), false)
                                .reduce((first, second) -> second).orElse(null),
                        violation.getMessage())).toList();
    }

    public static String generatePassword(String password) {
        Hash hash = Password.hash(password).addPepper(HASH_SECRET).with(bcrypt);
        return hash.getResult();
    }

    public static Boolean validatePassword(String password, String savedPassword) {
        return Password.check(password, savedPassword).addPepper(HASH_SECRET)
                .with(bcrypt);
    }

}