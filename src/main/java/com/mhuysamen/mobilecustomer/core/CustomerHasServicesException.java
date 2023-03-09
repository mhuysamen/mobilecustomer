package com.mhuysamen.mobilecustomer.core;

import com.mhuysamen.mobilecustomer.domain.CustomerIdentifier;

public class CustomerHasServicesException extends RuntimeException {
    public CustomerHasServicesException(CustomerIdentifier customerId) {
        super("Customer [%d] still has services assigned".formatted(customerId.getValue()));
    }
    
}
