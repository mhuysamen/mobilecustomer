package com.mhuysamen.mobilecustomer.domain;

import java.util.regex.Pattern;

import lombok.NonNull;
import lombok.Value;

@Value
public class IDCard {

    public class InvalidIDCardException extends RuntimeException {
        InvalidIDCardException(final String idCard) {
            super("Invalid ID Card: %s".formatted(idCard));
        }
    }

    private static final String VALID_ID_CARD = "^.{7}[MGAPLHBZ]$";

    @NonNull
    private final String value;

    private static boolean validateIdCard(final String idCard) {
        return Pattern.matches(VALID_ID_CARD, idCard);
    }

    public IDCard(final String idCard) {
        if(!validateIdCard(idCard)) {
            throw new InvalidIDCardException(idCard);
        }

        this.value = idCard;
    }

    @Override
    public String toString() {
        return new String(this.value);
    }
}
