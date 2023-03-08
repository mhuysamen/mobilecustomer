package com.mhuysamen.mobilecustomer.service.rest;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mhuysamen.mobilecustomer.domain.Customer;
import com.mhuysamen.mobilecustomer.domain.CustomerIdentifier;
import com.mhuysamen.mobilecustomer.domain.IDCard;

import lombok.Data;

@JsonNaming(SnakeCaseStrategy.class)
@Data
public class CustomerV1 {
    private Integer id;
    @NotEmpty(message = "id_card is required")
    private String idCard;
    @NotEmpty(message = "name must be provided")
    private String name;
    @NotNull(message = "surname field must be passed, even if empty")
    private String surname;
    private String address;

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
