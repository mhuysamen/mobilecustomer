package com.mhuysamen.mobilecustomer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class Customer {

    private CustomerIdentifier id;
    @NonNull
    private IDCard idCard;
    @NonNull
    private String name;
    @NonNull
    private String surname;
    private String address;

}
