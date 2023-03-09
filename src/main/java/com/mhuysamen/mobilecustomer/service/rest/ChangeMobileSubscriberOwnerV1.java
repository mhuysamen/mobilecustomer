package com.mhuysamen.mobilecustomer.service.rest;

import javax.validation.constraints.NotNull;

public class ChangeMobileSubscriberOwnerV1 {
    @NotNull(message = "owner must be specified")
    public Integer owner;
}
