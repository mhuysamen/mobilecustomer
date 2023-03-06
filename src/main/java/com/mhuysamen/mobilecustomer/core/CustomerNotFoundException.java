package com.mhuysamen.mobilecustomer.core;

import com.mhuysamen.mobilecustomer.domain.CustomerIdentifier;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(final CustomerIdentifier customerId) {
        super("Customer [%d] not found".formatted(customerId.getValue()));
    }
}
