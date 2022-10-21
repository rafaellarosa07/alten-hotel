package com.alten.hotel.dto;

import lombok.Data;

@Data
public class NewUserDTO {

  private Long id;

  private String name;

  private String username;

  private String password;

  private String document;
}
