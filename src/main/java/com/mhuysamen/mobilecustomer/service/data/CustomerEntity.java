package com.mhuysamen.mobilecustomer.service.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Entity(name = "customer")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public String idCard;
    public String name;
    public String surname;
    public String address;
}
