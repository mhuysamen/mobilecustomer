package com.mhuysamen.mobilecustomer.service.rest;

import java.time.Instant;

public class MobileSubscriberV1 {
    public Integer id;
    public String msidn;
    public Integer customerIdOwner;
    public Integer customerIdUser;
    public String serviceType;
    public Instant serviceStartDate;
}
