package com.mhuysamen.mobilecustomer.service.data;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MobileSubscriberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;
    public String msisdn;
    public Integer customerIdOwner;
    public Integer customerIdUser;
    public String serviceType;
    public Timestamp serviceStartDate;
}
