package com.alten.hotel.service;

import com.alten.hotel.dto.ReservationDTO;

import java.util.List;

public interface ReservationService {

  List<ReservationDTO> getAll();

  ReservationDTO findById(long id);

  ReservationDTO reserveARoom(ReservationDTO reservationDTO);

  ReservationDTO modifyAReservation(ReservationDTO reservationDTO);

  void cancelAReservation(Long numberReservation);
}
