package com.alten.hotel.dto;

import com.alten.hotel.enumaration.ReservationStatus;
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
  private UserViewDTO userId;
  private RoomDTO room;
  private ReservationStatus status;



  @Override
  public int hashCode() {
    return  id.intValue() * userId.hashCode() * number.hashCode();
  }

}
