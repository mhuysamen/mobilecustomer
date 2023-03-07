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

    private Customer modelFromEntity(CustomerEntity entity) {
        return new Customer(new CustomerIdentifier(entity.id), 
            new IDCard(entity.idCard), 
            entity.name, entity.surname, entity.address);
    }

    private CustomerEntity entityFromModel(Customer model) {
        CustomerEntity entity = new CustomerEntity();
        entity.id = model.getId() != null ? model.getId().getValue() : null;
        entity.idCard = model.getIdCard().getValue();
        entity.name = model.getName();
        entity.surname = model.getSurname();
        entity.address = model.getAddress();
        return entity;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        CustomerEntity newCustomer = repository.save(entityFromModel(customer));
        customer.setId(new CustomerIdentifier(newCustomer.id));
        return customer;
    }

    @Override
    public Optional<Customer> fetchCustomerById(CustomerIdentifier customer_id) {
        Optional<CustomerEntity> entity = repository.findById(customer_id.getValue());
        if(entity.isPresent()) {
            return Optional.of(modelFromEntity(entity.get()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Customer> fetchCustomerByIdCard(IDCard id_card) {
        Optional<CustomerEntity> entity = repository.findByIdCard(id_card.getValue());
        if(entity.isPresent()) {
            return Optional.of(modelFromEntity(entity.get()));
        }
        return Optional.empty();
    }

    @Override
    public void removeCustomer(CustomerIdentifier customer_id) {
        repository.deleteById(customer_id.getValue());        
    }

    @Override
    public void updateCustomer(Customer customer) {
        if(customer.getId() == null) {
            throw new RuntimeException();
        }
        
        repository.save(entityFromModel(customer));
    }

}
