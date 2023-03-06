package com.mhuysamen.mobilecustomer.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
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
