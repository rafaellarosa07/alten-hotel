package com.alten.hotel.dto;

import com.alten.hotel.enumaration.RoomStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {

  private Long id;

  private Long number;

  private RoomStatus status;

}
