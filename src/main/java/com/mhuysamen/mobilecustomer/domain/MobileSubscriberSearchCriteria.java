package com.mhuysamen.mobilecustomer.domain;

import java.time.Instant;

import com.mhuysamen.mobilecustomer.domain.MobileSubscriber.ServiceType;

public class MobileSubscriberSearchCriteria {
    public CustomerIdentifier owner;
    public CustomerIdentifier user;
    public ServiceType serviceType;
    public Instant serviceStartDateBefore;
    public Instant serviceStartDateAfter;
}
