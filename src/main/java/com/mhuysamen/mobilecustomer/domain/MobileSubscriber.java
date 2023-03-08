package com.mhuysamen.mobilecustomer.domain;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Data
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
