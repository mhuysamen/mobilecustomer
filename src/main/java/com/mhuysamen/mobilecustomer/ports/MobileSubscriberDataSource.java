package com.mhuysamen.mobilecustomer.ports;

import java.util.List;
import java.util.Optional;

import com.mhuysamen.mobilecustomer.domain.CustomerIdentifier;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriber;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriberIdentifier;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriberSearchCriteria;
import com.mhuysamen.mobilecustomer.domain.PhoneNumber;

public interface MobileSubscriberDataSource {
    List<MobileSubscriber> fetchAllSubscribers();

    List<MobileSubscriber> findMatchingSubscribers(MobileSubscriberSearchCriteria search_criteria);

    MobileSubscriber createSubscriber(MobileSubscriber subscriber);

    Optional<MobileSubscriber> fetchMobileSubscriberByPhoneNumber(PhoneNumber msisdn);

    Optional<MobileSubscriber> fetchMobileSubscriberById(MobileSubscriberIdentifier mobileSubscriberId);

    Integer countMobileSubcribersByOwnedOrUsedByCustomer(CustomerIdentifier customerId);

    void updateMobileSubscriber(MobileSubscriber subscriber);

    void removeMobileSubscriber(MobileSubscriberIdentifier mobileSubscriberId);
}
