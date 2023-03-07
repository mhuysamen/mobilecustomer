package com.mhuysamen.mobilecustomer.service.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mhuysamen.mobilecustomer.domain.Customer;
import com.mhuysamen.mobilecustomer.domain.CustomerIdentifier;
import com.mhuysamen.mobilecustomer.domain.IDCard;

@JsonNaming(SnakeCaseStrategy.class)
public class CustomerV1 {
    public Integer id;
    public String idCard;
    public String name;
    public String surname;
    public String address;

    @JsonIgnore
    final public static String MEDIA_TYPE_JSON = "application/vnd.mhuysamen.com.customer.v1+json";

    public static Customer toCustomer(CustomerV1 data) {
        return new Customer(
            data.id != null ? new CustomerIdentifier(data.id) : null, 
            new IDCard(data.idCard), 
            data.name, data.surname, data.address);
    }

    public static CustomerV1 fromCustomer(Customer customer) {
        CustomerV1 data = new CustomerV1();
        data.id = customer.getId().getValue();
        data.idCard = customer.getIdCard().getValue();
        data.name = customer.getName();
        data.surname = customer.getSurname();
        data.address = customer.getAddress();
        return data;
    }
}
