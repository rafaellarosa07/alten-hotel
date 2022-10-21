package com.alten.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO implements Serializable {

  private Long id;
  private Long number;
  private LocalDateTime dateCheckIn;
  private LocalDateTime dateCheckOut;
  private Long userId;
  private RoomDTO room;


  @Override
  public int hashCode() {
    return  id.intValue() * userId.hashCode() * number.hashCode();
  }

}
