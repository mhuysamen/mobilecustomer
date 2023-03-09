package com.mhuysamen.mobilecustomer.service.data;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MobileSubscriberRepository extends CrudRepository<MobileSubscriberEntity, Integer> {
    Optional<MobileSubscriberEntity> findByMsisdn(final String msisdn);

    Integer countByCustomerIdOwnerOrCustomerIdUser(final Integer customerIdOwner, final Integer customerIdUser);
}
