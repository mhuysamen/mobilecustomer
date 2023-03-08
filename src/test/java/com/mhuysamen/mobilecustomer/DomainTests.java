package com.mhuysamen.mobilecustomer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.Instant;

import com.mhuysamen.mobilecustomer.domain.Customer;
import com.mhuysamen.mobilecustomer.domain.CustomerIdentifier;
import com.mhuysamen.mobilecustomer.domain.IDCard;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriber;
import com.mhuysamen.mobilecustomer.domain.PhoneNumber;
import com.mhuysamen.mobilecustomer.domain.IDCard.InvalidIDCardException;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriber.ServiceType;
import com.mhuysamen.mobilecustomer.domain.PhoneNumber.InvalidPhoneNumber;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriberIdentifier;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriberSearchCriteria;

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

		Customer customer1 = new Customer(
			null,
			new IDCard("ABCDEFGM"),
			"Random",
			"Person",
			null);

        Customer customer2 = new Customer(
            new CustomerIdentifier(9999),
            new IDCard("ABCDEFGZ"),
            "Shrek",
            "Ogre",
            "Swamp");

        assertTrue(customer.equals(customer1));
        assertFalse(customer.equals(customer2));
    
        assertTrue(customer.hashCode() == customer1.hashCode());
        assertFalse(customer.hashCode() == customer2.hashCode());

        assertTrue(customer.toString().contains("Random"));

        customer2.setAddress("Castle");
        customer2.setName("Fiona");
        customer2.setSurname("Princess");
        customer2.setIdCard(new IDCard("PRINCESH"));
        customer2.setId(new CustomerIdentifier(1000));

        assertThrows(NullPointerException.class, () -> {
            customer2.setName(null);
        });

        assertThrows(NullPointerException.class, () -> {
            customer2.setSurname(null);
        });
        
        assertThrows(NullPointerException.class, () -> {
            customer2.setIdCard(null);
        });
        
	}

    @Test
    void createCustomerFailTest() {
        assertThrows(NullPointerException.class, () -> {
            new Customer(null, null, null, null, null);
        });

        assertThrows(NullPointerException.class, () -> {
            new Customer(null, new IDCard("IDCARDXZ"), null, null, null);
        });

        assertThrows(NullPointerException.class, () -> {
            new Customer(null, new IDCard("IDCARDXZ"), 
                "Donkey", null, null);
        });

    }

    @Test
	void createMobileSubscriberPREPAIDTest() {

        Instant now = Instant.now();

		MobileSubscriber subscriber = new MobileSubscriber(
			new MobileSubscriberIdentifier(Integer.valueOf(1)), 
			new PhoneNumber("35699123456"), 
			new CustomerIdentifier(Integer.valueOf(1)), 
			new CustomerIdentifier(Integer.valueOf(2)), 
			ServiceType.MOBILE_PREPAID, now);

        assertTrue(subscriber.getId().getValue().intValue() == 1);
		assertTrue(subscriber.getMsisdn().getValue().equals("35699123456"));
		assertTrue(subscriber.getOwner().getValue().equals(1));
		assertTrue(subscriber.getUser().getValue().equals(2));
		assertTrue(subscriber.getServiceType().equals(ServiceType.MOBILE_PREPAID));
        assertEquals(now, subscriber.getServiceStartDate());

        assertNotNull(subscriber.toString());
	}

    @Test
	void createMobileSubscriberPOSTPAIDTest() {

        Instant now = Instant.now();

		MobileSubscriber subscriber = new MobileSubscriber(
			new MobileSubscriberIdentifier(Integer.valueOf(1)), 
			new PhoneNumber("35699123456"), 
			new CustomerIdentifier(Integer.valueOf(1)), 
			new CustomerIdentifier(Integer.valueOf(2)), 
			ServiceType.MOBILE_POSTPAID, now);

        assertTrue(subscriber.getId().getValue().intValue() == 1);
		assertTrue(subscriber.getMsisdn().getValue().equals("35699123456"));
		assertTrue(subscriber.getOwner().getValue().equals(1));
		assertTrue(subscriber.getUser().getValue().equals(2));
		assertTrue(subscriber.getServiceType().equals(ServiceType.MOBILE_POSTPAID));
        assertEquals(now, subscriber.getServiceStartDate());
	}

    @Test
	void createMobileSubscriberNullTest() {

        assertThrows(NullPointerException.class, () -> {
		    new MobileSubscriber(null, null, null, null, null, null);
        });

        assertThrows(NullPointerException.class, () -> {
		    new MobileSubscriber(null, 
                new PhoneNumber("35699123456"), 
                null, 
                null, 
                null, 
                null);
        });

        assertThrows(NullPointerException.class, () -> {
		    new MobileSubscriber(null, 
                new PhoneNumber("35699123456"), 
                new CustomerIdentifier(Integer.valueOf(1)), 
                null, 
                null, 
                null);
        });

        assertThrows(NullPointerException.class, () -> {
		    new MobileSubscriber(null, 
                new PhoneNumber("35699123456"), 
                new CustomerIdentifier(Integer.valueOf(1)), 
                new CustomerIdentifier(Integer.valueOf(2)), 
                ServiceType.MOBILE_POSTPAID,
                null);
        });

        assertNotNull(new MobileSubscriber(null, 
            new PhoneNumber("35699123456"), 
            new CustomerIdentifier(Integer.valueOf(1)), 
            new CustomerIdentifier(Integer.valueOf(2)), 
            ServiceType.MOBILE_POSTPAID,
            Instant.now()));

	}

    @Test
    void setMobileSubscriberTest() {
		MobileSubscriber subscriber = new MobileSubscriber(
			new MobileSubscriberIdentifier(Integer.valueOf(1)), 
			new PhoneNumber("35699123456"), 
			new CustomerIdentifier(Integer.valueOf(1)), 
			new CustomerIdentifier(Integer.valueOf(2)), 
			ServiceType.MOBILE_POSTPAID, Instant.now());

        subscriber.setId(null);
		
		assertThrows(NullPointerException.class, () -> {
            subscriber.setMsisdn(null);
		});

        assertThrows(NullPointerException.class, () -> {
            subscriber.setOwner(null);
		});

        assertThrows(NullPointerException.class, () -> {
            subscriber.setUser(null);
		});

        assertThrows(NullPointerException.class, () -> {
            subscriber.setServiceType(null);
		});

        assertThrows(NullPointerException.class, () -> {
            subscriber.setServiceStartDate(null);
		});

        subscriber.setMsisdn(new PhoneNumber("35699123456"));
        subscriber.setOwner(new CustomerIdentifier(1));
        subscriber.setUser(new CustomerIdentifier(1));
        subscriber.setServiceType(ServiceType.MOBILE_POSTPAID);
        subscriber.setServiceStartDate(Instant.now());

        assertNotNull(subscriber.getMsisdn());
        assertNotNull(subscriber.getOwner());
        assertNotNull(subscriber.getUser());
        assertNotNull(subscriber.getServiceType());
        assertNotNull(subscriber.getServiceStartDate());
    }

    @Test
    void equalMobileSubscriberTest() {
        Instant now = Instant.now();

		MobileSubscriber subscriber = new MobileSubscriber(
			new MobileSubscriberIdentifier(Integer.valueOf(1)), 
			new PhoneNumber("35699123456"), 
			new CustomerIdentifier(Integer.valueOf(1)), 
			new CustomerIdentifier(Integer.valueOf(2)), 
			ServiceType.MOBILE_POSTPAID, now);

        MobileSubscriber subscriberSame = new MobileSubscriber(
            new MobileSubscriberIdentifier(Integer.valueOf(1)), 
            new PhoneNumber("35699123456"), 
            new CustomerIdentifier(Integer.valueOf(1)), 
            new CustomerIdentifier(Integer.valueOf(2)), 
            ServiceType.MOBILE_POSTPAID, now);

        MobileSubscriber subscriberDifferent1 = new MobileSubscriber(
            new MobileSubscriberIdentifier(Integer.valueOf(2)), 
            new PhoneNumber("35699123456"), 
            new CustomerIdentifier(Integer.valueOf(1)), 
            new CustomerIdentifier(Integer.valueOf(2)), 
            ServiceType.MOBILE_POSTPAID, now);

        MobileSubscriber subscriberDifferent2 = new MobileSubscriber(
            new MobileSubscriberIdentifier(Integer.valueOf(1)), 
            new PhoneNumber("35699000000"), 
            new CustomerIdentifier(Integer.valueOf(1)), 
            new CustomerIdentifier(Integer.valueOf(2)), 
            ServiceType.MOBILE_POSTPAID, now);
    
        MobileSubscriber subscriberDifferent3 = new MobileSubscriber(
            new MobileSubscriberIdentifier(Integer.valueOf(1)), 
            new PhoneNumber("35699123456"), 
            new CustomerIdentifier(Integer.valueOf(2)), 
            new CustomerIdentifier(Integer.valueOf(2)), 
            ServiceType.MOBILE_POSTPAID, now);
    
        MobileSubscriber subscriberDifferent4 = new MobileSubscriber(
            new MobileSubscriberIdentifier(Integer.valueOf(1)), 
            new PhoneNumber("35699123456"), 
            new CustomerIdentifier(Integer.valueOf(1)), 
            new CustomerIdentifier(Integer.valueOf(1)), 
            ServiceType.MOBILE_POSTPAID, now);

        MobileSubscriber subscriberDifferent5 = new MobileSubscriber(
            new MobileSubscriberIdentifier(Integer.valueOf(1)), 
            new PhoneNumber("35699123456"), 
            new CustomerIdentifier(Integer.valueOf(1)), 
            new CustomerIdentifier(Integer.valueOf(2)), 
            ServiceType.MOBILE_POSTPAID, Instant.now().plus(Duration.ofMinutes(5)));
        
        MobileSubscriber subscriberDifferentAll = new MobileSubscriber(
            new MobileSubscriberIdentifier(Integer.valueOf(2)), 
            new PhoneNumber("35699000000"), 
            new CustomerIdentifier(Integer.valueOf(2)), 
            new CustomerIdentifier(Integer.valueOf(3)), 
            ServiceType.MOBILE_POSTPAID, Instant.now());

            
        assertTrue(subscriber.equals(subscriberSame));
        assertFalse(subscriber.equals(subscriberDifferent1));
        assertFalse(subscriber.equals(subscriberDifferent2));
        assertFalse(subscriber.equals(subscriberDifferent3));
        assertFalse(subscriber.equals(subscriberDifferent4));
        assertFalse(subscriber.equals(subscriberDifferent5));
        assertFalse(subscriber.equals(subscriberDifferentAll));

        assertTrue(subscriber.hashCode() == subscriberSame.hashCode());
        assertFalse(subscriber.hashCode() == subscriberDifferent1.hashCode());


    }

    @Test
    void createCustomerIdentifierTest() {
        CustomerIdentifier c = new CustomerIdentifier(1);
        assertEquals(Integer.valueOf(1), c.getValue());

        assertThrows(NullPointerException.class, () -> {
            new CustomerIdentifier(null);
        });

        CustomerIdentifier c1 = new CustomerIdentifier(1);
        CustomerIdentifier c2 = new CustomerIdentifier(2);

        assertTrue(c.equals(c1));
        assertFalse(c.equals(c2));
        assertTrue(c.hashCode() == c1.hashCode());
        assertFalse(c.hashCode() == c2.hashCode());

        assertEquals("1", c.toString());
    }

    @Test
    void createMobileSubscriberIdentifierTest() {
        MobileSubscriberIdentifier m = new MobileSubscriberIdentifier(1);
        assertEquals(Integer.valueOf(1), m.getValue());

        assertThrows(NullPointerException.class, () -> {
            new MobileSubscriberIdentifier(null);
        });

        MobileSubscriberIdentifier m1 = new MobileSubscriberIdentifier(1);
        MobileSubscriberIdentifier m2 = new MobileSubscriberIdentifier(2);

        assertTrue(m.equals(m1));
        assertFalse(m.equals(m2));
        assertTrue(m.hashCode() == m1.hashCode());
        assertFalse(m.hashCode() == m2.hashCode());

        assertEquals("1", m.toString());
    }

    @Test
    void createMobileSubscriberSearchCriteriaTest() {
        assertNotNull(new MobileSubscriberSearchCriteria());
    }

    @Test
    void createPhoneNumberTest() {
        assertThrows(InvalidPhoneNumber.class, () -> {
            new PhoneNumber("1234");
        });

        assertThrows(InvalidPhoneNumber.class, () -> {
            new PhoneNumber(null);
        });

        PhoneNumber pn = new PhoneNumber("35699123456");
        assertNotNull(pn);
        assertEquals("35699123456", pn.toString());

        PhoneNumber pn1 = new PhoneNumber("35699123456");
        PhoneNumber pn2 = new PhoneNumber("35699000000");

        assertTrue(pn.equals(pn1));
        assertFalse(pn.equals(pn2));
        assertTrue(pn.hashCode() == pn1.hashCode());
        assertFalse(pn.hashCode() == pn2.hashCode());

    }

    @Test
    void createIDCardTest() {
        assertThrows(NullPointerException.class, () -> {
            new IDCard(null);
        });

        assertThrows(InvalidIDCardException.class, () -> {
            new IDCard("1234");
        });

        IDCard idc = new IDCard("1234567Z");
        assertNotNull(idc);
        assertEquals("1234567Z", idc.toString());

        IDCard idc1 = new IDCard("1234567Z");
        IDCard idc2 = new IDCard("1234567A");

        assertTrue(idc.equals(idc1));
        assertFalse(idc.equals(idc2));
        assertTrue(idc.hashCode() == idc1.hashCode());
        assertFalse(idc.hashCode() == idc2.hashCode());
    }
}
