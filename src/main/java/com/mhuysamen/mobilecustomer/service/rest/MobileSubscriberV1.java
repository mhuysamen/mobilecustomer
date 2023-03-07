package com.mhuysamen.mobilecustomer.service.rest;

import java.time.Instant;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mhuysamen.mobilecustomer.domain.CustomerIdentifier;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriber;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriberIdentifier;
import com.mhuysamen.mobilecustomer.domain.PhoneNumber;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriber.ServiceType;

@JsonNaming(SnakeCaseStrategy.class)
public class MobileSubscriberV1 {
    public Integer id;
    @NotEmpty
    public String msisdn;
    @NotEmpty
    public Integer owner;
    @NotEmpty
    public Integer user;
    @NotEmpty
    public ServiceType serviceType;
    @NotEmpty
    public Instant serviceStartDate;

    @JsonIgnore
    final public static String MEDIA_TYPE_JSON = "application/vnd.mhuysamen.com.mobilesubscriber.v1+json";

    public static MobileSubscriber toMobileSubscriber(MobileSubscriberV1 data) {
        return new MobileSubscriber(
            data.id != null ? new MobileSubscriberIdentifier(data.id) : null, 
            new PhoneNumber(data.msisdn), 
            new CustomerIdentifier(data.owner), 
            new CustomerIdentifier(data.user), 
            data.serviceType, 
            data.serviceStartDate);
    }

    public static MobileSubscriberV1 fromMobileSubscriber(MobileSubscriber subscriber) {
        MobileSubscriberV1 data = new MobileSubscriberV1();
        data.id = subscriber.getId().getValue();
        data.msisdn = subscriber.getMsisdn().getValue();
        data.owner = subscriber.getOwner().getValue();
        data.user = subscriber.getUser().getValue();
        data.serviceType = subscriber.getServiceType();
        data.serviceStartDate = subscriber.getServiceStartDate();
        return data;
    }
}
