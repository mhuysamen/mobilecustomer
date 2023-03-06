package com.mhuysamen.mobilecustomer;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.i18n.phonenumbers.NumberParseException;

@SpringBootTest
public class GooglePhoneLibTests {
    
    @Test
	void testGooglePhoneNumber() {
		PhoneNumberUtil pnu = PhoneNumberUtil.getInstance();

		try {
			PhoneNumber pn = pnu.parse("35699123456", "MT");
			System.out.println("Phone number is: +%s%s".formatted(pn.getCountryCode(), pn.getNationalNumber()));
			if(!pnu.isValidNumber(pn)) {
				System.out.println("Number is INVALID!");
			}
		}
		catch(NumberParseException e) {
			System.out.println("Invalid phone number");
			System.out.println(e.getMessage());
		}
	}

}
