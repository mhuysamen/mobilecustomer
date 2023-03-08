package com.mhuysamen.mobilecustomer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.mhuysamen.mobilecustomer.core.CustomerAlreadyExistsException;
import com.mhuysamen.mobilecustomer.core.CustomerNotFoundException;
import com.mhuysamen.mobilecustomer.core.MobileCustomerServiceImpl;
import com.mhuysamen.mobilecustomer.domain.Customer;
import com.mhuysamen.mobilecustomer.domain.CustomerIdentifier;
import com.mhuysamen.mobilecustomer.domain.IDCard;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriber;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriberIdentifier;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriberSearchCriteria;
import com.mhuysamen.mobilecustomer.domain.PhoneNumber;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriber.ServiceType;
import com.mhuysamen.mobilecustomer.ports.CustomerDataSource;
import com.mhuysamen.mobilecustomer.ports.MobileSubscriberDataSource;

import ch.qos.logback.core.util.Duration;

@SpringBootTest
public class CoreTests {
    
    @MockBean
    private CustomerDataSource customerData;

    @MockBean
    private MobileSubscriberDataSource subscriberData;

    @Autowired
    private MobileCustomerServiceImpl service;

    private Customer customerFiona(final Integer id) {
        return new Customer(
            id != null ? new CustomerIdentifier(id) : null,
            new IDCard("PRINCESH"),
            "Fiona",
            "Princess",
            "Castle"
        );
    }

    private Customer customerShrek(final Integer id) {
        return new Customer(
            id != null ? new CustomerIdentifier(id) : null,
            new IDCard("THEOGREZ"),
            "Shrek",
            "Ogre",
            "Swamp"
        );
    }

    private Customer customerDonkey(final Integer id) {
        return new Customer(
            id != null ? new CustomerIdentifier(id) : null,
            new IDCard("ADONKEYG"),
            "Donkey",
            "Talking",
            "Forest"
        );
    }

    private MobileSubscriber subscriberShrek(final Integer id) {
        // Shrek is on a PREPAID contract, owned by Fiona
        return new MobileSubscriber(
            id != null ? new MobileSubscriberIdentifier(id) : null, 
            new PhoneNumber("35699111111"), 
            new CustomerIdentifier(2), 
            new CustomerIdentifier(1), 
            ServiceType.MOBILE_PREPAID, 
            Instant.ofEpochMilli(1678284009L));
    }

    private MobileSubscriber subscriberFiona(final Integer id) {
        // Fiona is on a POSTPAID contract, owned by herself
        return new MobileSubscriber(
            id != null ? new MobileSubscriberIdentifier(id) : null, 
            new PhoneNumber("35699222222"), 
            new CustomerIdentifier(2), 
            new CustomerIdentifier(2), 
            ServiceType.MOBILE_POSTPAID, 
            Instant.ofEpochMilli(1678284009L));
    }

    private MobileSubscriber subscriberDonkeyBusiness(final Integer id) {
        // Donkey is on a POSTPAID contract, owned by herself, for his consulting business
        return new MobileSubscriber(
            id != null ? new MobileSubscriberIdentifier(id) : null, 
            new PhoneNumber("35699333333"), 
            new CustomerIdentifier(3), 
            new CustomerIdentifier(3), 
            ServiceType.MOBILE_POSTPAID, 
            Instant.ofEpochMilli(1678284009L));
    }

    private MobileSubscriber subscriberDonkeyPersonal(final Integer id) {
        // Donkey also has a phone on a PREPAID contract for personal use
        return new MobileSubscriber(
            id != null ? new MobileSubscriberIdentifier(id) : null, 
            new PhoneNumber("35699333444"), 
            new CustomerIdentifier(3), 
            new CustomerIdentifier(3), 
            ServiceType.MOBILE_PREPAID, 
            Instant.ofEpochMilli(1678284009L));
    }

    private List<MobileSubscriber> allSubscribers() {
        List<MobileSubscriber> subscribers = new ArrayList<>();
        subscribers.add(subscriberShrek(1));
        subscribers.add(subscriberFiona(2));
        subscribers.add(subscriberDonkeyBusiness(3));
        subscribers.add(subscriberDonkeyPersonal(4));
        return subscribers;
    }

    @Test
    void service_CreateCustomerTest() {
        Customer shrek = customerShrek(null);

        Mockito.when(customerData.createCustomer(shrek)).thenReturn(customerShrek(1));
        Mockito.when(customerData.fetchCustomerByIdCard(shrek.getIdCard())).thenReturn(Optional.empty());

        CustomerIdentifier shrekId = service.addCustomer(shrek);
        assertNotNull(shrekId);

        shrek.setId(shrekId);

        Mockito.when(customerData.fetchCustomerByIdCard(shrek.getIdCard())).thenReturn(Optional.of(shrek));
        Mockito.when(customerData.fetchCustomerById(shrekId)).thenReturn(Optional.of(shrek));

        assertThrows(CustomerAlreadyExistsException.class, () -> {
            service.addCustomer(shrek);
        });
    }

    @Test
    void service_UpdateCustomer() {
        Customer shrek = customerShrek(1);
        assertThrows(CustomerNotFoundException.class, () -> {
            service.updateCustomer(shrek.getId(), shrek);
        });

        Mockito.when(customerData.fetchCustomerByIdCard(shrek.getIdCard())).thenReturn(Optional.of(shrek));
        Mockito.when(customerData.fetchCustomerById(shrek.getId())).thenReturn(Optional.of(shrek));

        Customer fiona = customerFiona(2);

        Mockito.when(customerData.fetchCustomerByIdCard(fiona.getIdCard())).thenReturn(Optional.of(fiona));
        Mockito.when(customerData.fetchCustomerById(fiona.getId())).thenReturn(Optional.of(fiona));

        // Change something on existing customer
        shrek.setAddress("The Swamp");
        service.updateCustomer(shrek.getId(), shrek);

        // Set customer IdCard same as existing customer IdCard
        fiona.setIdCard(new IDCard(shrek.getIdCard().getValue()));
        assertThrows(CustomerAlreadyExistsException.class, () -> {
            service.updateCustomer(fiona.getId(), fiona);
        });

        Customer shrekClone = customerShrek(1);
        shrekClone.setIdCard(new IDCard("XXXXXXXP"));

        service.updateCustomer(shrekClone.getId(), shrekClone);
    }

    @Test
    void service_removeCustomer() {
        Customer shrek = customerShrek(1);

        Mockito.when(customerData.fetchCustomerByIdCard(shrek.getIdCard())).thenReturn(Optional.of(shrek));
        Mockito.when(customerData.fetchCustomerById(shrek.getId())).thenReturn(Optional.of(shrek));

        service.removeCustomer(shrek.getId());

        Mockito.when(customerData.fetchCustomerByIdCard(shrek.getIdCard())).thenReturn(Optional.empty());
        Mockito.when(customerData.fetchCustomerById(shrek.getId())).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> {
            service.removeCustomer(shrek.getId());
        });
    }

    @Test
    void service_listAllSubscribers() {
        List<MobileSubscriber> subscribers = allSubscribers();

        Mockito.when(subscriberData.fetchAllSubscribers()).thenReturn(subscribers);

        List<MobileSubscriber> answer = service.listAllMobileSubscribers();

        assertEquals(subscribers, answer);
    }

    @Test
    void service_listMatchingSubscribers() {
        List<MobileSubscriber> subscribers = allSubscribers();

        MobileSubscriber shrek = subscribers.get(0);
        MobileSubscriber fiona = subscribers.get(1);

        Mockito.when(subscriberData.fetchAllSubscribers()).thenReturn(subscribers);
        for (MobileSubscriber mobileSubscriber : subscribers) {
            Mockito.when(subscriberData.fetchMobileSubscriberByPhoneNumber(mobileSubscriber.getMsisdn()))
                .thenReturn(Optional.of(mobileSubscriber));
            Mockito.when(subscriberData.fetchMobileSubscriberById(mobileSubscriber.getId()))
                .thenReturn(Optional.of(mobileSubscriber));                
        }

        Mockito.when(subscriberData.findMatchingSubscribers(new MobileSubscriberSearchCriteria() {{
            msisdn = shrek.getMsisdn();    
        }})).thenReturn(subscribers.subList(0, 1));

        MobileSubscriberSearchCriteria criteria = new MobileSubscriberSearchCriteria();
        criteria.msisdn = shrek.getMsisdn();

        List<MobileSubscriber> answer = service.listMatchingSubscribers(criteria);

        // assertEquals(1, answer.size());
    }

}
