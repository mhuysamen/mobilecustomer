package com.mhuysamen.mobilecustomer.domain;

import lombok.NonNull;
import lombok.Value;

@Value
public class CustomerIdentifier {
    @NonNull
    private Integer value;

    @Override
    public String toString() {
        return this.value.toString();
    }
}