package com.client.ws.rasmooplus.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.client.ws.rasmooplus.model.mysql.UserType;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Long> {
}
