package com.alten.hotel.dto;

import com.alten.hotel.enumaration.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO implements Serializable {

  private Long id;


  private Long number;

  @NotNull(message = "Date of check in may not be null")
  private LocalDateTime dateCheckIn;

  @NotNull(message = "Date of check out may not be null")
  private LocalDateTime dateCheckOut;
  @NotNull(message = "User may not be null")
  private UserViewDTO userId;

  @NotNull(message = "Room may not be null")
  private RoomDTO room;

  private ReservationStatus status;



  @Override
  public int hashCode() {
    return  id.intValue() * userId.hashCode() * number.hashCode();
  }

}
