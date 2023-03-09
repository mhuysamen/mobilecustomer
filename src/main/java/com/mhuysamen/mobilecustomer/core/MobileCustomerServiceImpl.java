package com.mhuysamen.mobilecustomer.core;

import java.util.List;
import java.util.Optional;

import com.mhuysamen.mobilecustomer.domain.Customer;
import com.mhuysamen.mobilecustomer.domain.CustomerIdentifier;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriber;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriberSearchCriteria;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriber.ServiceType;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriberIdentifier;
import com.mhuysamen.mobilecustomer.ports.CustomerDataSource;
import com.mhuysamen.mobilecustomer.ports.MobileCustomerService;
import com.mhuysamen.mobilecustomer.ports.MobileSubscriberDataSource;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MobileCustomerServiceImpl implements MobileCustomerService {

    @NonNull
    private CustomerDataSource customerData;
    @NonNull
    private MobileSubscriberDataSource mobileSubscriberData;

    @Override
    public CustomerIdentifier addCustomer(Customer customer) {
        Optional<Customer> existingCustomer = customerData.fetchCustomerByIdCard(customer.getIdCard());

        if(existingCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException(customer);
        }
        
        return customerData.createCustomer(customer).getId();
    }

    @Override
    public Customer getCustomer(CustomerIdentifier customerId) {
        Optional<Customer> existingCustomer = customerData.fetchCustomerById(customerId);

        if(!existingCustomer.isPresent()) {
            throw new CustomerNotFoundException(customerId);
        }
        
        return existingCustomer.get();
    }

    @Override
    public MobileSubscriberIdentifier addMobileSubscriber(MobileSubscriber subscriber) {
        Optional<MobileSubscriber> existingMobileSubscription = mobileSubscriberData.fetchMobileSubscriberByPhoneNumber(subscriber.getMsisdn());

        if(existingMobileSubscription.isPresent()) {
            throw new MobileSubscriberAlreadyExistsException(subscriber);
        }

        return mobileSubscriberData.createSubscriber(subscriber).getId();
    }
    
    @Override
    public void changeMobileSubscriptionPlan(MobileSubscriberIdentifier subscriberId, ServiceType serviceType) {

        Optional<MobileSubscriber> oSubscriber = mobileSubscriberData.fetchMobileSubscriberById(subscriberId);

        if(!oSubscriber.isPresent()) {
            throw new MobileSubscriberNotFoundException(subscriberId);
        }

        MobileSubscriber subscriber = oSubscriber.get();
        subscriber.setServiceType(serviceType);

        mobileSubscriberData.updateMobileSubscriber(subscriber);
    }

    @Override
    public List<MobileSubscriber> listAllMobileSubscribers() {
        
        return mobileSubscriberData.fetchAllSubscribers();
    }

    @Override
    public List<MobileSubscriber> listMatchingSubscribers(MobileSubscriberSearchCriteria criteria) {
        return mobileSubscriberData.findMatchingSubscribers(criteria);
    }

    @Override
    public void removeCustomer(CustomerIdentifier customerId) {
        Optional<Customer> customer = customerData.fetchCustomerById(customerId);
        if(!customer.isPresent()) {
            throw new CustomerNotFoundException(customerId);
        }

        // Check for the existence of Subscriptions owned or used by Customer.
        if(mobileSubscriberData.countMobileSubcribersByOwnedOrUsedByCustomer(customerId) > 0) {
            throw new CustomerHasServicesException(customerId);
        }

        customerData.removeCustomer(customerId);
    }

    @Override
    public void removeMobileSubscriber(MobileSubscriberIdentifier subscriberId) {
        Optional<MobileSubscriber> subscriber = mobileSubscriberData.fetchMobileSubscriberById(subscriberId);
        if(!subscriber.isPresent()) {
            throw new MobileSubscriberNotFoundException(subscriberId);
        }
        mobileSubscriberData.removeMobileSubscriber(subscriberId);
    }

    @Override
    public void updateCustomer(CustomerIdentifier customerId, Customer newDetails) {
        Optional<Customer> oCustomer = customerData.fetchCustomerById(customerId);

        if(!oCustomer.isPresent()) {
            throw new CustomerNotFoundException(customerId);
        }

        Optional<Customer> oDuplicate = customerData.fetchCustomerByIdCard(newDetails.getIdCard());

        if(oDuplicate.isPresent() && !oDuplicate.get().getId().equals(customerId)) {
            throw new CustomerAlreadyExistsException(newDetails);
        }

        newDetails.setId(customerId);
        customerData.updateCustomer(newDetails);
    }

    @Override
    public void updateMobileSubscriptionOwner(MobileSubscriberIdentifier subscriberId, CustomerIdentifier newOwner) {
        Optional<MobileSubscriber> oSubscriber = mobileSubscriberData.fetchMobileSubscriberById(subscriberId);

        if(!oSubscriber.isPresent()) {
            throw new MobileSubscriberNotFoundException(subscriberId);
        }

        Optional<Customer> oCustomer = customerData.fetchCustomerById(newOwner);

        if(!oCustomer.isPresent()) {
            throw new CustomerNotFoundException(newOwner);
        }

        MobileSubscriber subscriber = oSubscriber.get();
        subscriber.setOwner(newOwner);

        mobileSubscriberData.updateMobileSubscriber(subscriber);
        
    }

    @Override
    public void updateMobileSubscriptionUser(MobileSubscriberIdentifier subscriberId, CustomerIdentifier newUser) {
        Optional<MobileSubscriber> oSubscriber = mobileSubscriberData.fetchMobileSubscriberById(subscriberId);

        if(!oSubscriber.isPresent()) {
            throw new MobileSubscriberNotFoundException(subscriberId);
        }

        Optional<Customer> oCustomer = customerData.fetchCustomerById(newUser);

        if(!oCustomer.isPresent()) {
            throw new CustomerNotFoundException(newUser);
        }

        MobileSubscriber subscriber = oSubscriber.get();
        subscriber.setUser(newUser);

        mobileSubscriberData.updateMobileSubscriber(subscriber);
    }

}
