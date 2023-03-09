package com.mhuysamen.mobilecustomer.service.rest;

import javax.validation.constraints.NotNull;

public class ChangeMobileSubscriberUserV1 {
    @NotNull(message = "user must be specified")
    public Integer user;
}
