package com.client.ws.rasmooplus.repository.mysql;

import com.client.ws.rasmooplus.model.mysql.SubscriptionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionTypeRepository extends JpaRepository<SubscriptionType, Long> {

    Optional<SubscriptionType> findByProductKey(String productKey);
}
