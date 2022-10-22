package com.alten.hotel.service;

import com.alten.hotel.dto.RoomDTO;
import com.alten.hotel.enumaration.RoomStatus;

import java.util.List;

sealed public interface RoomService permits RoomServiceImpl {

  List<RoomDTO> getAll();

  RoomDTO findById(long id);

  RoomStatus checkAvailability(long id);

  List<RoomDTO> getAllAvailableRooms(RoomStatus status);
}
