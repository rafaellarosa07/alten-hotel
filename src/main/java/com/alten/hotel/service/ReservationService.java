package com.alten.hotel.service;

import com.alten.hotel.dto.ReservationDTO;
import com.alten.hotel.dto.RoomDTO;

import java.util.List;

sealed public interface ReservationService permits ReservationServiceImpl {

  List<ReservationDTO> getAll();

  ReservationDTO findById(long id);
}
