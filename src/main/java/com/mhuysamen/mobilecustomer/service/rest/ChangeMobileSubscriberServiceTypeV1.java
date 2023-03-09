package com.mhuysamen.mobilecustomer.service.rest;

import javax.validation.constraints.NotNull;

import com.mhuysamen.mobilecustomer.domain.MobileSubscriber.ServiceType;

public class ChangeMobileSubscriberServiceTypeV1 {
    @NotNull(message = "service_type is required")
    public ServiceType serviceType;
}
