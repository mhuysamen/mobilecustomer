package com.mhuysamen.mobilecustomer.core;

import com.mhuysamen.mobilecustomer.domain.MobileSubscriber;

public class MobileSubscriberAlreadyExistsException extends RuntimeException {
    public MobileSubscriberAlreadyExistsException(MobileSubscriber subscriber) {
        super("Mobile Subscriber [%s] already exists!".formatted(subscriber.getMsisdn()));
    }
    
}
