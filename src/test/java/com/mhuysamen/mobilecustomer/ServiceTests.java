package com.mhuysamen.mobilecustomer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.mhuysamen.mobilecustomer.core.CustomerNotFoundException;
import com.mhuysamen.mobilecustomer.domain.Customer;
import com.mhuysamen.mobilecustomer.domain.CustomerIdentifier;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriber;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriberIdentifier;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriber.ServiceType;
import com.mhuysamen.mobilecustomer.ports.MobileCustomerService;
import com.mhuysamen.mobilecustomer.service.data.CustomerEntity;
import com.mhuysamen.mobilecustomer.service.data.CustomerRepository;
import com.mhuysamen.mobilecustomer.service.data.MobileSubscriberEntity;
import com.mhuysamen.mobilecustomer.service.data.MobileSubscriberRepository;
import com.mhuysamen.mobilecustomer.service.rest.CustomerV1;

@SpringBootTest
public class ServiceTests {
    
    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private MobileSubscriberRepository subscriberRepository;

    @Autowired
    private MobileCustomerService service;

    private CustomerEntity customerFiona(final Integer id) {
        CustomerEntity entity = new CustomerEntity();
        entity.id = id;
        entity.idCard = "PRINCESH";
        entity.name = "Fiona";
        entity.surname = "Princess";
        entity.address = "Castle";
        return entity;
    }

    private CustomerEntity customerShrek(final Integer id) {
        CustomerEntity entity = new CustomerEntity();
        entity.id = id;
        entity.idCard = "THEOGREZ";
        entity.name = "Shrek";
        entity.surname = "Ogre";
        entity.address = "Swamp";
        return entity;
    }

    private CustomerEntity customerDonkey(final Integer id) {
        CustomerEntity entity = new CustomerEntity();
        entity.id = id;
        entity.idCard = "ADONKEYG";
        entity.name = "Donkey";
        entity.surname = "Talking";
        entity.address = "Forest";
        return entity;
    }

    private MobileSubscriberEntity subscriberShrek(final Integer id) {
        // Shrek is on a PREPAID contract, owned by Fiona
        MobileSubscriberEntity entity = new MobileSubscriberEntity();
        entity.id = id;
        entity.msisdn = "35699111111";
        entity.customerIdOwner = 2;
        entity.customerIdUser = 1;
        entity.serviceType = "MOBILE_PREPAID";
        entity.serviceStartDate = Timestamp.from(Instant.ofEpochMilli(1678284009L));
        return entity;
    }

    private MobileSubscriberEntity subscriberFiona(final Integer id) {
        // Shrek is on a PREPAID contract, owned by Fiona
        MobileSubscriberEntity entity = new MobileSubscriberEntity();
        entity.id = id;
        entity.msisdn = "35699222222";
        entity.customerIdOwner = 2;
        entity.customerIdUser = 2;
        entity.serviceType = "MOBILE_POSTPAID";
        entity.serviceStartDate = Timestamp.from(Instant.ofEpochMilli(1678284009L));
        return entity;
    }

    private MobileSubscriberEntity subscriberDonkeyBusiness(final Integer id) {
        // Shrek is on a PREPAID contract, owned by Fiona
        MobileSubscriberEntity entity = new MobileSubscriberEntity();
        entity.id = id;
        entity.msisdn = "35699333333";
        entity.customerIdOwner = 3;
        entity.customerIdUser = 3;
        entity.serviceType = "MOBILE_POSTPAID";
        entity.serviceStartDate = Timestamp.from(Instant.ofEpochMilli(1678284009L));
        return entity;
    }

    private MobileSubscriberEntity subscriberDonkeyPersonal(final Integer id) {
        // Shrek is on a PREPAID contract, owned by Fiona
        MobileSubscriberEntity entity = new MobileSubscriberEntity();
        entity.id = id;
        entity.msisdn = "35699333444";
        entity.customerIdOwner = 3;
        entity.customerIdUser = 3;
        entity.serviceType = "MOBILE_PREPAID";
        entity.serviceStartDate = Timestamp.from(Instant.ofEpochMilli(1678284009L));
        return entity;
    }
    
    private List<CustomerEntity> allCustomers() {
        List<CustomerEntity> customers = new ArrayList<>();
        customers.add(customerShrek(1));
        customers.add(customerFiona(2));
        customers.add(customerDonkey(3));
        return customers;
    }
    private List<MobileSubscriberEntity> allSubscribers() {
        List<MobileSubscriberEntity> subscribers = new ArrayList<>();
        subscribers.add(subscriberShrek(1));
        subscribers.add(subscriberFiona(2));
        subscribers.add(subscriberDonkeyBusiness(3));
        subscribers.add(subscriberDonkeyPersonal(4));
        return subscribers;
    }

    @BeforeEach
    void setupCustomerData() {
        for (CustomerEntity c : allCustomers()) {
            Mockito.when(customerRepository.findById(c.id)).thenReturn(Optional.of(c));
            Mockito.when(customerRepository.findByIdCard(c.idCard)).thenReturn(Optional.of(c));
            Mockito.when(customerRepository.save(c)).thenReturn(c);            
        }
    }

    @BeforeEach
    void setupSubscriberData() {
        List<MobileSubscriberEntity> subscribers = allSubscribers();
        for (MobileSubscriberEntity s : subscribers) {
            Mockito.when(subscriberRepository.save(s)).thenReturn(s);
            Mockito.when(subscriberRepository.findById(s.id)).thenReturn(Optional.of(s));
            Mockito.when(subscriberRepository.findByMsisdn(s.msisdn)).thenReturn(Optional.of(s));
        }

        Mockito.when(subscriberRepository.findAll()).thenReturn(subscribers);
    }

    @Test
    void service_listMobileSubscribersTests() {
        List<MobileSubscriber> list = service.listAllMobileSubscribers();

        assertEquals(4, list.size());
    }

    @Test
    void service_createCustomerTest() {
        CustomerEntity customerData = new CustomerEntity();
        customerData.idCard = "TEST123Z";
        customerData.name = "Danger";
        customerData.surname = "Stranger";
        customerData.address = "Shadows";

        CustomerEntity customerStored = new CustomerEntity();
        customerStored.idCard = "TEST123Z";
        customerStored.name = "Danger";
        customerStored.surname = "Stranger";
        customerStored.address = "Shadows";
        customerStored.id = 4;

        CustomerV1 customerV1 = new CustomerV1();
        customerV1.setIdCard(customerData.idCard);
        customerV1.setName(customerData.name);
        customerV1.setSurname(customerData.surname);
        customerV1.setAddress(customerData.address);

        Customer customer = CustomerV1.toCustomer(customerV1);

        assertNotNull(customer);

        Mockito.when(customerRepository.save(customerData)).thenReturn(customerStored);

        CustomerIdentifier customerId = service.addCustomer(customer);

        assertEquals(customerStored.id, customerId.getValue());
    }

    @Test
    void service_removeCustomerTest() {
        service.removeCustomer(new CustomerIdentifier(1));

        assertThrows(CustomerNotFoundException.class, () -> {
            service.removeCustomer(new CustomerIdentifier(5));
        });

    }

    @Test
    void service_updateCustomerTest() {
        CustomerEntity customerData = allCustomers().get(0);

        CustomerV1 customerV1 = new CustomerV1();
        customerV1.setId(customerData.id);
        customerV1.setIdCard(customerData.idCard);
        customerV1.setName(customerData.name);
        customerV1.setSurname(customerData.surname);
        customerV1.setAddress(customerData.address);

        Customer customer = CustomerV1.toCustomer(customerV1);

        service.updateCustomer(customer.getId(), customer);

        assertThrows(RuntimeException.class, () -> {
            customer.setId(null);
            service.updateCustomer(null, customer);
        });
    }

    @Test
    void service_changeMobileSubscriberServicePlanTest() {
        MobileSubscriberEntity subscriberEntity = allSubscribers().get(3);
        MobileSubscriberIdentifier subscriberId = new MobileSubscriberIdentifier(subscriberEntity.id);

        service.changeMobileSubscriptionPlan(subscriberId, ServiceType.MOBILE_POSTPAID);
    }
}
