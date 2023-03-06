package com.mhuysamen.mobilecustomer.core;

import com.mhuysamen.mobilecustomer.domain.MobileSubscriberIdentifier;

public class MobileSubscriberNotFoundException extends RuntimeException {
    public MobileSubscriberNotFoundException(MobileSubscriberIdentifier subscriberId) {
        super("Mobile Subscriber [%d] not found!".formatted(subscriberId.getValue()));
    }
    
}
