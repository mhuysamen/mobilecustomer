package com.mhuysamen.mobilecustomer.service.data;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mhuysamen.mobilecustomer.domain.MobileSubscriber;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriberIdentifier;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriberSearchCriteria;
import com.mhuysamen.mobilecustomer.domain.PhoneNumber;
import com.mhuysamen.mobilecustomer.ports.MobileSubscriberDataSource;

@Component
public class MobileSubscriberDataSourceImpl implements MobileSubscriberDataSource {
    @Autowired
    private MobileSubscriberRepository repository;

    @Override
    public MobileSubscriber createSubscriber(MobileSubscriber subscriber) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<MobileSubscriber> fetchAllSubscribers(MobileSubscriberIdentifier start, Integer count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<MobileSubscriber> fetchMobileSubscriberById(MobileSubscriberIdentifier mobileSubscriberId) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public Optional<MobileSubscriber> fetchMobileSubscriberByPhoneNumber(PhoneNumber msisdn) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public List<MobileSubscriber> findMatchingSubscribers(MobileSubscriberSearchCriteria search_criteria) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateMobileSubscriber(MobileSubscriber subscriber) {
        // TODO Auto-generated method stub
        
    }
}
