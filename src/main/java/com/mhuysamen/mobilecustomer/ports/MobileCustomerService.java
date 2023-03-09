package com.mhuysamen.mobilecustomer.ports;

import java.util.List;

import com.mhuysamen.mobilecustomer.domain.Customer;
import com.mhuysamen.mobilecustomer.domain.CustomerIdentifier;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriber;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriberSearchCriteria;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriber.ServiceType;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriberIdentifier;

public interface MobileCustomerService {
    List<MobileSubscriber> listAllMobileSubscribers();
    List<MobileSubscriber> listMatchingSubscribers(MobileSubscriberSearchCriteria criteria);

    MobileSubscriberIdentifier addMobileSubscriber(MobileSubscriber subscriber);
    CustomerIdentifier addCustomer(Customer customer);
    Customer getCustomer(CustomerIdentifier customerId);
    void changeMobileSubscriptionPlan(MobileSubscriberIdentifier subscriber, ServiceType serviceType);
    void updateCustomer(CustomerIdentifier customerId, Customer newDetails);
    void removeMobileSubscriber(MobileSubscriberIdentifier subscriberId);
    void removeCustomer(CustomerIdentifier customerId);
    void updateMobileSubscriptionOwner(MobileSubscriberIdentifier subscriberId, CustomerIdentifier newOwner);
    void updateMobileSubscriptionUser(MobileSubscriberIdentifier subscriberId, CustomerIdentifier newUser);
}
