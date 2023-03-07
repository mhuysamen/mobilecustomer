package com.mhuysamen.mobilecustomer.ports;

import java.util.Optional;

import com.mhuysamen.mobilecustomer.domain.Customer;
import com.mhuysamen.mobilecustomer.domain.CustomerIdentifier;
import com.mhuysamen.mobilecustomer.domain.IDCard;

public interface CustomerDataSource {
    public Optional<Customer> fetchCustomerById(CustomerIdentifier customer_id);

    public Optional<Customer> fetchCustomerByIdCard(IDCard id_card);
    
    public Customer createCustomer(Customer customer);

    public void updateCustomer(Customer customer);

    public void removeCustomer(CustomerIdentifier customer_id);
}
