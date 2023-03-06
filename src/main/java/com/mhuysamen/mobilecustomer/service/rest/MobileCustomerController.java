package com.mhuysamen.mobilecustomer.service.rest;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mhuysamen.mobilecustomer.domain.Customer;
import com.mhuysamen.mobilecustomer.domain.CustomerIdentifier;
import com.mhuysamen.mobilecustomer.domain.IDCard;
import com.mhuysamen.mobilecustomer.ports.MobileCustomerService;

@RestController
public class MobileCustomerController {
    
    @Autowired
    private MobileCustomerService mobileCustomerService;

    @RequestMapping(
        consumes = "application/vnd.mhuysamen.com.customer.v1+json",
        method = RequestMethod.POST,
        value = "/customers"
        )
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Object> createCustomer(@RequestBody final CustomerV1 newCustomer) {
        Customer customer = new Customer(null, 
        new IDCard(newCustomer.idCard), 
        newCustomer.name, newCustomer.surname, newCustomer.address);

        CustomerIdentifier customerId = mobileCustomerService.addCustomer(customer);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest().path("/{id}")
            .buildAndExpand(customerId.getValue())
            .toUri();

        return ResponseEntity.created(location).build();
    }
}
