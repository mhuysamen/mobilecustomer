package com.mhuysamen.mobilecustomer.domain;

import lombok.NonNull;
import lombok.Value;

@Value
public class MobileSubscriberIdentifier {
    @NonNull
    private Integer value;

    @Override
    public String toString() {
        return value.toString();
    }
}