package com.mhuysamen.mobilecustomer.domain;

import java.time.Instant;

import com.mhuysamen.mobilecustomer.domain.MobileSubscriber.ServiceType;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class MobileSubscriberSearchCriteria {
    private int criteriaCount;

    private PhoneNumber msisdn;
    private CustomerIdentifier owner;
    private CustomerIdentifier user;
    private ServiceType serviceType;
    private Instant serviceStartDateBefore;
    private Instant serviceStartDateAfter;

    public MobileSubscriberSearchCriteria() {
        criteriaCount = 0;
    }

    public boolean hasCriteria() {
        return criteriaCount > 0;
    }

    private void changeCounter(Object existing, Object newvalue) {
        if(existing == null && newvalue != null) {
            criteriaCount++;
            return;
        }

        if(existing != null && newvalue == null) {
            criteriaCount--;
            return;
        }
    }

    public void setMsisdn(final PhoneNumber pn) {
        changeCounter(this.msisdn, pn);
        this.msisdn = pn;
    }

    public void setOwner(final CustomerIdentifier id) {
        changeCounter(this.owner, id);
        this.owner = id;
    }

    public void setUser(final CustomerIdentifier id) {
        changeCounter(this.user, id);
        this.user = id;
    }

    public void setServiceType(final ServiceType st) {
        changeCounter(this.serviceType, st);
        this.serviceType = st;
    }

    public void setServiceStartDateBefore(final Instant inst) {
        changeCounter(this.serviceStartDateBefore, inst);
        this.serviceStartDateBefore = inst;
    }

    public void setServiceStartDateAfter(final Instant inst) {
        changeCounter(this.serviceStartDateAfter, inst);
        this.serviceStartDateAfter = inst;
    }
}
