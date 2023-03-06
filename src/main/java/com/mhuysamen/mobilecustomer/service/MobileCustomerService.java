package com.mhuysamen.mobilecustomer.service;

import org.springframework.stereotype.Service;

import com.mhuysamen.mobilecustomer.core.MobileCustomerServiceImpl;
import com.mhuysamen.mobilecustomer.ports.CustomerDataSource;
import com.mhuysamen.mobilecustomer.ports.MobileSubscriberDataSource;

@Service
public class MobileCustomerService extends MobileCustomerServiceImpl {

    public MobileCustomerService(final CustomerDataSource customerDataSource, final MobileSubscriberDataSource subscriberDataSource) {
        super(customerDataSource, subscriberDataSource);
    }
}
