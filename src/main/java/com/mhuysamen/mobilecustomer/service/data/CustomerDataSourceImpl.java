package com.mhuysamen.mobilecustomer.service.data;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mhuysamen.mobilecustomer.domain.Customer;
import com.mhuysamen.mobilecustomer.domain.CustomerIdentifier;
import com.mhuysamen.mobilecustomer.domain.IDCard;
import com.mhuysamen.mobilecustomer.ports.CustomerDataSource;

@Component
public class CustomerDataSourceImpl implements CustomerDataSource {
    @Autowired
    private CustomerRepository repository;

    @Override
    public Customer createCustomer(Customer customer) {
        CustomerEntity newCustomer = new CustomerEntity();
        newCustomer.idCard = customer.getIdCard().getValue();
        newCustomer.name = customer.getName();
        newCustomer.surname = customer.getSurname();
        newCustomer.address = customer.getAddress();

        newCustomer = repository.save(newCustomer);
        customer.setId(new CustomerIdentifier(newCustomer.id));
        return customer;
    }

    @Override
    public Optional<Customer> fetchCustomerById(CustomerIdentifier customer_id) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public Optional<Customer> fetchCustomerByIdCard(IDCard id_card) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public void removeCustomer(Customer customer) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateCustomer(Customer customer) {
        // TODO Auto-generated method stub
        
    }

}
