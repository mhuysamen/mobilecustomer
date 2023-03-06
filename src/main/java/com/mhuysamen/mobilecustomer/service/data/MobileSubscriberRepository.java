package com.mhuysamen.mobilecustomer.service.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MobileSubscriberRepository extends CrudRepository<MobileSubscriberEntity, Integer> {
    
}
