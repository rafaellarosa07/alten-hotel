package com.alten.hotel.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "HOTEL")
@Data
public class Hotel {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "NAME", nullable = false)
  private String name;

  @Column(name = "ADDRESS", nullable = false)
  private String adress;
}
