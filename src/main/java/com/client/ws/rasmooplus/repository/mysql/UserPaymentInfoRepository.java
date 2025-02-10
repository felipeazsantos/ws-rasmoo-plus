package com.client.ws.rasmooplus.repository.mysql;

import com.client.ws.rasmooplus.model.mysql.UserPaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPaymentInfoRepository extends JpaRepository<UserPaymentInfo, Long> {
}
