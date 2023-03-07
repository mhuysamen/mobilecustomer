package com.mhuysamen.mobilecustomer.service.data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mhuysamen.mobilecustomer.domain.CustomerIdentifier;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriber;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriberIdentifier;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriberSearchCriteria;
import com.mhuysamen.mobilecustomer.domain.PhoneNumber;
import com.mhuysamen.mobilecustomer.domain.MobileSubscriber.ServiceType;
import com.mhuysamen.mobilecustomer.ports.MobileSubscriberDataSource;

@Component
public class MobileSubscriberDataSourceImpl implements MobileSubscriberDataSource {
    @Autowired
    private MobileSubscriberRepository repository;

    @Autowired
    private EntityManager entityManager;

    private MobileSubscriberEntity entityFromModel(final MobileSubscriber model) {
        MobileSubscriberEntity entity = new MobileSubscriberEntity();

        entity.id = model.getId() != null ? model.getId().getValue() : null;
        entity.msisdn = model.getMsisdn().getValue();
        entity.customerIdOwner = model.getOwner().getValue();
        entity.customerIdUser = model.getUser().getValue();
        entity.serviceStartDate = Timestamp.from(model.getServiceStartDate());
        entity.serviceType = model.getServiceType().name();

        return entity;
    }

    private MobileSubscriber modelFromEntity(final MobileSubscriberEntity entity) {
        MobileSubscriber model = new MobileSubscriber(
            new MobileSubscriberIdentifier(entity.id), new PhoneNumber(entity.msisdn), 
            new CustomerIdentifier(entity.customerIdOwner), 
            new CustomerIdentifier(entity.customerIdUser), 
            ServiceType.valueOf(entity.serviceType), 
            entity.serviceStartDate.toInstant());

        return model;
    }

    @Override
    public MobileSubscriber createSubscriber(MobileSubscriber subscriber) {
        MobileSubscriberEntity newSubscriber = entityFromModel(subscriber);

        newSubscriber = repository.save(newSubscriber);
        subscriber.setId(new MobileSubscriberIdentifier(newSubscriber.id));

        return subscriber;
    }

    @Override
    public List<MobileSubscriber> fetchAllSubscribers() {
        List<MobileSubscriber> subscribers = new ArrayList<MobileSubscriber>();
        repository.findAll().forEach(entity -> {
            subscribers.add(modelFromEntity(entity));
        });
        return subscribers;
    }

    @Override
    public Optional<MobileSubscriber> fetchMobileSubscriberById(MobileSubscriberIdentifier mobileSubscriberId) {
        Optional<MobileSubscriberEntity> entity = repository.findById(mobileSubscriberId.getValue());
        if(entity.isPresent()) {
            return Optional.of(modelFromEntity(entity.get()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<MobileSubscriber> fetchMobileSubscriberByPhoneNumber(PhoneNumber msisdn) {
        Optional<MobileSubscriberEntity> entity = repository.findByMsisdn(msisdn.getValue());
        if(entity.isPresent()) {
            return Optional.of(modelFromEntity(entity.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<MobileSubscriber> findMatchingSubscribers(MobileSubscriberSearchCriteria sc) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MobileSubscriberEntity> cq = cb.createQuery(MobileSubscriberEntity.class);

        List<Predicate> predicates = new ArrayList<Predicate>();

        Root<MobileSubscriberEntity> subscriber = cq.from(MobileSubscriberEntity.class);
        if(sc.msisdn != null) {
            predicates.add(cb.equal(subscriber.get("msisdn"), sc.msisdn.getValue()));
        }
        if(sc.serviceType != null) {
            predicates.add(cb.equal(subscriber.get("serviceType"), sc.serviceType.name()));
        }
        if(sc.owner != null) {
            predicates.add(cb.equal(subscriber.get("customerIdOwner"), sc.owner.getValue()));
        }
        if(sc.user != null) {
            predicates.add(cb.equal(subscriber.get("customerIdUser"), sc.user.getValue()));
        }
        if(sc.serviceStartDateAfter != null) {
            predicates.add(cb.greaterThanOrEqualTo(subscriber.get("serviceStartDate"), Timestamp.from(sc.serviceStartDateAfter)));
        }
        if(sc.serviceStartDateBefore != null) {
            predicates.add(cb.lessThanOrEqualTo(subscriber.get("serviceStartDate"), Timestamp.from(sc.serviceStartDateBefore)));
        }

        cq.where(cb.and(predicates.toArray(new Predicate[0])));

        List<MobileSubscriber> subscribers = new ArrayList<>();

        entityManager.createQuery(cq).getResultList().forEach(entity -> {
            subscribers.add(modelFromEntity(entity));
        });

        return subscribers;
    }

    @Override
    public void updateMobileSubscriber(MobileSubscriber subscriber) {
        // This situation shouldn't arise
        if(subscriber.getId() == null) {
            throw new RuntimeException();
        }

        repository.save(entityFromModel(subscriber));
    }

    @Override
    public void removeMobileSubscriber(MobileSubscriberIdentifier mobileSubscriberId) {
        repository.deleteById(mobileSubscriberId.getValue());        
    }
}
