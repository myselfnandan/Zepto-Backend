package com.zepto.zepto_backend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zepto.zepto_backend.model.User;

@Repository
public interface ConsumerRepository extends JpaRepository<User , UUID>{

  public User findByEmail(String email);
}
