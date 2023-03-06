package com.mhuysamen.mobilecustomer.core;

import com.mhuysamen.mobilecustomer.domain.Customer;

public class CustomerAlreadyExistsException extends RuntimeException {
    public CustomerAlreadyExistsException(final Customer customer) {
        super("Customer with ID Card [%s] already exists".formatted(customer.getIdCard()));
    }
}
