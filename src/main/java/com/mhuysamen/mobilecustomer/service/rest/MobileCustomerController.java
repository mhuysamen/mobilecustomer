package com.mhuysamen.mobilecustomer.service.rest;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mhuysamen.mobilecustomer.domain.Customer;
import com.mhuysamen.mobilecustomer.domain.CustomerIdentifier;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriber;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriberIdentifier;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriberSearchCriteria;
import com.mhuysamen.mobilecustomer.domain.PhoneNumber;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriber.ServiceType;
import com.mhuysamen.mobilecustomer.ports.MobileCustomerService;

@RestController
public class MobileCustomerController {

    @Autowired
    private MobileCustomerService mobileCustomerService;

    @PostMapping(value = "/customers", consumes = CustomerV1.MEDIA_TYPE_JSON)
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Object> createCustomer(@Valid @RequestBody final CustomerV1 newCustomer) {
        Customer customer = CustomerV1.toCustomer(newCustomer);

        CustomerIdentifier customerId = mobileCustomerService.addCustomer(customer);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest().path("/{id}")
            .buildAndExpand(customerId.getValue())
            .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/customers/{id}", produces = CustomerV1.MEDIA_TYPE_JSON)
    @ResponseBody
    public CustomerV1 getCustomer(
            @PathVariable("id") final Integer customerId) {
        Customer customer = mobileCustomerService.getCustomer(new CustomerIdentifier(customerId));
        return CustomerV1.fromCustomer(customer);
    }

    @PutMapping(value = "/customers/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> updateCustomer(
            @PathVariable("id") final Integer customerId, 
            @Valid @RequestBody final CustomerV1 existingCustomer) {

        mobileCustomerService.updateCustomer(new CustomerIdentifier(customerId), CustomerV1.toCustomer(existingCustomer));

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/customers/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteCustomer(@PathVariable("id") final Integer customerId) {
        mobileCustomerService.removeCustomer(new CustomerIdentifier(customerId));

        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/subscribers", consumes = MobileSubscriberV1.MEDIA_TYPE_JSON)
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Object> addMobileSubscriber(@Valid @RequestBody final MobileSubscriberV1 newSubscriber) {
        MobileSubscriber subscriber = MobileSubscriberV1.toMobileSubscriber(newSubscriber);

        MobileSubscriberIdentifier subscriberId = mobileCustomerService.addMobileSubscriber(subscriber);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest().path("/{id}")
            .buildAndExpand(subscriberId.getValue())
            .toUri();

        return ResponseEntity.created(location).build();
    }

    @PostMapping(value = "/subscribers/{id}/change_service_type", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Object> changeMobileSubscriberServiceType(
            @PathVariable final Integer id,
            @RequestBody @Valid final ChangeMobileSubscriberServiceTypeV1 changes) {

        MobileSubscriberIdentifier subscriberId = new MobileSubscriberIdentifier(id);

        ServiceType serviceType = changes.serviceType;

        mobileCustomerService.changeMobileSubscriptionPlan(subscriberId, serviceType);

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/subscribers/{id}/change_owner", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Object> changeMobileSubscriberOwner(
            @PathVariable final Integer id,
            @RequestBody @Valid final ChangeMobileSubscriberOwnerV1 changes) {

        MobileSubscriberIdentifier subscriberId = new MobileSubscriberIdentifier(id);

        CustomerIdentifier newOwner = new CustomerIdentifier(changes.owner);

        mobileCustomerService.updateMobileSubscriptionOwner(subscriberId, newOwner);

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/subscribers/{id}/change_user", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Object> changeMobileSubscriberUser(
            @PathVariable final Integer id,
            @RequestBody @Valid final ChangeMobileSubscriberUserV1 changes) {

        MobileSubscriberIdentifier subscriberId = new MobileSubscriberIdentifier(id);

        CustomerIdentifier newUser = new CustomerIdentifier(changes.user);

        mobileCustomerService.updateMobileSubscriptionUser(subscriberId, newUser);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/subscribers/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteMobileSubscriber(@PathVariable final Integer id) {
        mobileCustomerService.removeMobileSubscriber(new MobileSubscriberIdentifier(id));

        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/subscribers", produces = MobileSubscriberV1.MEDIA_TYPE_JSON)
    public List<MobileSubscriberV1> listMobileSubscribers(
            @RequestParam(name = "msisdn", required = false) String msisdn,
            @RequestParam(name = "owner", required = false) Integer owner,
            @RequestParam(name = "user", required = false) Integer user,
            @RequestParam(name = "service_type", required = false) ServiceType serviceType,
            @RequestParam(name = "start_date_before", required = false) Instant startDateBefore,
            @RequestParam(name = "start_date_after", required = false) Instant startDateAfter) {

        MobileSubscriberSearchCriteria criteria = new MobileSubscriberSearchCriteria();
        criteria.setMsisdn((msisdn != null ? new PhoneNumber(msisdn) : null));
        criteria.setOwner((owner != null ? new CustomerIdentifier(owner) : null));
        criteria.setUser((user != null ? new CustomerIdentifier(user) : null));
        criteria.setServiceType(serviceType);
        criteria.setServiceStartDateAfter(startDateAfter);
        criteria.setServiceStartDateBefore(startDateBefore);

        if(criteria.hasCriteria()) {
            return mobileCustomerService.listMatchingSubscribers(criteria)
                .stream().map(MobileSubscriberV1::fromMobileSubscriber).collect(Collectors.toList());
        }
        else {
            return mobileCustomerService.listAllMobileSubscribers()
                .stream().map(MobileSubscriberV1::fromMobileSubscriber).collect(Collectors.toList());
        }
    }

}
