package com.alten.hotel.service;

import com.alten.hotel.dto.ReservationDTO;

import java.util.List;

sealed public interface ReservationService permits ReservationServiceImpl {

  List<ReservationDTO> getAll();

  ReservationDTO findById(long id);

  ReservationDTO reserveARoom(ReservationDTO reservationDTO);

  ReservationDTO modifyAReservation(ReservationDTO reservationDTO);

  void cancelAReservation(Long numberReservation);
}
