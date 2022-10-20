package com.alten.hotel.dto;

import com.alten.hotel.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO implements Serializable {

  private Long id;
  private Long number;
  private LocalDateTime dateCheckOut;
  private Boolean dateCheckIn;
  private User user;

}
