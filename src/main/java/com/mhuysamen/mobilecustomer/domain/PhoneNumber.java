package com.mhuysamen.mobilecustomer.domain;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.NumberParseException;

import lombok.Value;

@Value
public class PhoneNumber {

    public class InvalidPhoneNumber extends RuntimeException {
        public InvalidPhoneNumber(final String value) {
            super("Invalid phone number: %s".formatted(value));
        }
    }

    private static PhoneNumberUtil phoneNumberUtil;

    static {
        phoneNumberUtil = PhoneNumberUtil.getInstance();
    }

    private final String value;
    
    public PhoneNumber(final String value) {
        try {
            com.google.i18n.phonenumbers.Phonenumber.PhoneNumber pn = phoneNumberUtil.parse(value, "MT");
            if(!phoneNumberUtil.isValidNumber(pn)) {
                throw new InvalidPhoneNumber(value);
            }

            // Convert any input into a E164 standard representation
            this.value = "%s%s".formatted(pn.getCountryCode(), pn.getNationalNumber());
        }
        catch(NumberParseException e) {
            throw new InvalidPhoneNumber(value);
        }
    }

    public String toString() {
        return value;
    }
}
