package com.mhuysamen.mobilecustomer.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mhuysamen.mobilecustomer.core.MobileCustomerServiceImpl;
import com.mhuysamen.mobilecustomer.domain.Customer;
import com.mhuysamen.mobilecustomer.domain.CustomerIdentifier;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriber;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriberIdentifier;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriber.ServiceType;
import com.mhuysamen.mobilecustomer.ports.CustomerDataSource;
import com.mhuysamen.mobilecustomer.ports.MobileSubscriberDataSource;

@Service
public class MobileCustomerService extends MobileCustomerServiceImpl {

    public MobileCustomerService(final CustomerDataSource customerDataSource, final MobileSubscriberDataSource subscriberDataSource) {
        super(customerDataSource, subscriberDataSource);
    }

    @Override
    @Transactional()
    public CustomerIdentifier addCustomer(Customer customer) {
        return super.addCustomer(customer);
    }

    @Override
    public Customer getCustomer(CustomerIdentifier customerId) {
        return super.getCustomer(customerId);
    }

    @Override
    @Transactional
    public MobileSubscriberIdentifier addMobileSubscriber(MobileSubscriber subscriber) {
        return super.addMobileSubscriber(subscriber);
    }

    @Override
    @Transactional
    public void changeMobileSubscriptionPlan(MobileSubscriberIdentifier subscriberId, ServiceType serviceType) {
        super.changeMobileSubscriptionPlan(subscriberId, serviceType);
    }

    @Override
    @Transactional
    public void removeCustomer(CustomerIdentifier customerId) {
        super.removeCustomer(customerId);
    }

    @Override
    @Transactional
    public void removeMobileSubscriber(MobileSubscriberIdentifier subscriberId) {
        super.removeMobileSubscriber(subscriberId);
    }

    @Override
    @Transactional
    public void updateCustomer(CustomerIdentifier customerId, Customer newDetails) {
        super.updateCustomer(customerId, newDetails);
    }

    @Override
    @Transactional
    public void updateMobileSubscriptionOwner(MobileSubscriberIdentifier subscriberId, CustomerIdentifier newOwner) {
        super.updateMobileSubscriptionOwner(subscriberId, newOwner);
    }

    @Override
    @Transactional
    public void updateMobileSubscriptionUser(MobileSubscriberIdentifier subscriberId, CustomerIdentifier newUser) {
        super.updateMobileSubscriptionUser(subscriberId, newUser);
    }
}
