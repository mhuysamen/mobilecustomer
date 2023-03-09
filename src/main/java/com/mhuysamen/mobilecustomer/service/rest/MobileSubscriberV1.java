package com.mhuysamen.mobilecustomer.service.rest;

import java.time.Instant;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mhuysamen.mobilecustomer.domain.CustomerIdentifier;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriber;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriberIdentifier;
import com.mhuysamen.mobilecustomer.domain.PhoneNumber;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriber.ServiceType;

import lombok.Data;

@JsonNaming(SnakeCaseStrategy.class)
@Data
public class MobileSubscriberV1 {
    private Integer id;
    @NotEmpty(message = "phone number is required")
    private String msisdn;
    @NotNull(message = "owner identifier must be provided")
    private Integer owner;
    @NotNull(message = "user identifier must be provided")
    private Integer user;
    @NotNull(message = "service_type must be specified")
    private ServiceType serviceType;
    // @NotEmpty(message = "service_start_date must be specified")
    private Instant serviceStartDate = Instant.now();

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
