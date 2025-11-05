package com.zepto.zepto_backend.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "consumer")

public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  private String username;
  private String email;
  private String password;
  private Long number;
  private String usertype;
  private String status;
}
