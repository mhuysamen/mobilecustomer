package com.mhuysamen.mobilecustomer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;

import com.mhuysamen.mobilecustomer.domain.Customer;
import com.mhuysamen.mobilecustomer.domain.CustomerIdentifier;
import com.mhuysamen.mobilecustomer.domain.IDCard;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriber;
import com.mhuysamen.mobilecustomer.domain.PhoneNumber;
import com.mhuysamen.mobilecustomer.domain.IDCard.InvalidIDCardException;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriber.ServiceType;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriberIdentifier;

@SpringBootTest
public class DomainTests {
    @Test
	void invalidIDCardTest() {
		Exception exception = assertThrows(InvalidIDCardException.class, () -> {
			new IDCard("Something");
		});

		String expectedMessage = "Invalid ID Card: Something";
		assertTrue(exception.getMessage().contains(expectedMessage));
	}

	@Test
	void validIDCardTest() {
		final String value = "1234567Z";
		IDCard idCard = new IDCard(value);

		assertTrue(idCard.getValue().equals(value));
	}

	@Test
	void validPhoneNumber() {
		final String value = "35699123456";
		PhoneNumber phoneNumber = new PhoneNumber(value);
		assertEquals(value, phoneNumber.getValue());
	}

	@Test
	void createCustomerTest() {
		Customer customer = new Customer(
			null,
			new IDCard("ABCDEFGM"),
			"Random",
			"Person",
			null);

		assertSame("Random", customer.getName());
		assertSame("Person", customer.getSurname());
		assertNull(customer.getAddress());
		assertNull(customer.getId());
	}

	void createMobileSubscriberTest() {

		MobileSubscriber subscriber = new MobileSubscriber(
			new MobileSubscriberIdentifier(Integer.valueOf(1)), 
			new PhoneNumber("35699123456"), 
			new CustomerIdentifier(Integer.valueOf(1)), 
			new CustomerIdentifier(Integer.valueOf(2)), 
			ServiceType.MOBILE_PREPAID, Instant.now());

		assertTrue(subscriber.getMsisdn().getValue().equals("35699123456"));
		assertTrue(subscriber.getOwner().getValue().equals(1));
		assertTrue(subscriber.getUser().getValue().equals(2));
		assertTrue(subscriber.getServiceType().equals(ServiceType.MOBILE_PREPAID));
	}
}
