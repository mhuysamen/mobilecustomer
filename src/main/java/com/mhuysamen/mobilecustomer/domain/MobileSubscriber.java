package com.mhuysamen.mobilecustomer.domain;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MobileSubscriber {

    public enum ServiceType {
        MOBILE_PREPAID, MOBILE_POSTPAID
    }

    private MobileSubscriberIdentifier id;
    @NonNull
    private PhoneNumber msisdn;
    @NonNull
    private CustomerIdentifier owner;
    @NonNull
    private CustomerIdentifier user;
    @NonNull
    private ServiceType serviceType;
    @NonNull
    private Instant serviceStartDate;
}
