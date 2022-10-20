package com.alten.hotel.service;

import com.alten.hotel.dto.RoomDTO;

import java.util.List;

sealed public interface RoomService permits RoomServiceImpl {

  List<RoomDTO> getAll();

  RoomDTO findById(long id);
}
