package com.alten.hotel.service;

import com.alten.hotel.dto.ReservationDTO;
import com.alten.hotel.enumaration.ReservationStatus;
import com.alten.hotel.enumaration.RoomStatus;
import com.alten.hotel.exception.ApiException;
import com.alten.hotel.exception.messages.Messages;
import com.alten.hotel.model.Reservation;
import com.alten.hotel.model.Room;
import com.alten.hotel.repository.ReservationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {


  private ReservationRepository repository;

  private RoomService roomService;
  private ModelMapper mapper;

  @Autowired
  public ReservationServiceImpl(ReservationRepository repository,
                                ModelMapper mapper, RoomService roomService) {
    this.repository = repository;
    this.mapper = mapper;
    this.roomService = roomService;
  }

  /**
   * Reserve a room
   *
   * @param reservationDTO
   * @return ReservationDTO
   */
  @Override
  public ReservationDTO reserveARoom(ReservationDTO reservationDTO) {
    validateReservation(reservationDTO);
    var reservation = mapper.map(reservationDTO, Reservation.class);
    reservation.setNumber(Long.valueOf(reservationDTO.hashCode()));
    reservation.setStatus(ReservationStatus.RESERVED);
    return mapper.map(repository.save(reservation), ReservationDTO.class);
  }


  /**
   * Check if reservation is ok
   *
   * @param reservationDTO
   * @return void
   */
  private void validateReservation(ReservationDTO reservationDTO) {
    checkRoomAvailable(reservationDTO.getRoom().getId());
    checkAvailability(reservationDTO);
    checkStay(reservationDTO.getDateCheckIn());
    checkNumberOfDays(reservationDTO.getDateCheckIn(), reservationDTO.getDateCheckOut());
    checkNumberOfDaysAdvance(reservationDTO.getDateCheckIn());
  }


  /**
   * Check if room is available
   *
   * @param idRoom
   * @return void
   */
  private void checkRoomAvailable(Long idRoom) {
    if (roomService.checkAvailability(idRoom).equals(RoomStatus.INTERDICTED)) {
      throw new ApiException(HttpStatus.EXPECTATION_FAILED,
              Messages.ERROR_ROOM_INTERDICTED);
    }
  }

  /**
   * Check reservation
   *
   * @param reservationDTO
   * @return void
   */
  private void checkAvailability(ReservationDTO reservationDTO) {
    getDates(reservationDTO).stream().forEach(date -> {
      if (repository.findReservationByDateBetweenPeriod(date).isPresent()) {
        throw new ApiException(HttpStatus.EXPECTATION_FAILED,
                Messages.ERROR_ROOM_NOT_AVAILABLE);
      }
    });
  }


  private List<LocalDate> getDates(ReservationDTO reservationDTO) {
    return reservationDTO.getDateCheckIn().toLocalDate()
            .datesUntil(reservationDTO.getDateCheckOut().toLocalDate())
            .collect(Collectors.toList());
  }

  /**
   * Check if stay is at least 1 day in advance
   *
   * @param checkIn
   * @return void
   */
  private void checkStay(LocalDateTime checkIn) {
    var today = LocalDate.now();
    if (!checkIn.toLocalDate().isAfter(today)) {
      throw new ApiException(HttpStatus.EXPECTATION_FAILED,
              Messages.ERROR_RESERVATION_1_DAY_ADVANCE);
    }
  }

  /**
   * Check if stay is longer than 3 days
   *
   * @param checkIn
   * @param checkOut
   * @return void
   */
  private void checkNumberOfDays(LocalDateTime checkIn, LocalDateTime checkOut) {
    if (!checkIn.plusDays(3).isAfter(checkOut)) {
      throw new ApiException(HttpStatus.EXPECTATION_FAILED,
              Messages.ERROR_RESERVATION_CANT_BE_LONGER_3_DAYS);
    }
  }

  /**
   * Check if stay is beeing reserved more than 30 days in advance
   *
   * @param checkIn
   * @return void
   */
  private void checkNumberOfDaysAdvance(LocalDateTime checkIn) {
    if (!LocalDate.now().plusDays(31).isAfter(checkIn.toLocalDate())) {
      throw new ApiException(HttpStatus.EXPECTATION_FAILED,
              Messages.ERROR_RESERVATION_MORE_30_DAYS_ADVANCE);
    }
  }


  /**
   * Modify a reservation
   *
   * @param reservationDTO
   * @return ReservationDTO
   */
  @Override
  public ReservationDTO modifyAReservation(ReservationDTO reservationDTO) {
    validateReservation(reservationDTO);
    var reservation = repository.findById(reservationDTO.getId())
            .orElseThrow(() ->
                    new ApiException(HttpStatus.NOT_FOUND,
                            Messages.ERROR_RESERVATION_NOT_FOUND));

    reservation.setRoom(mapper.map(reservationDTO.getRoom(), Room.class));
    reservation.setDateCheckIn(reservationDTO.getDateCheckIn());
    reservation.setDateCheckOut(reservationDTO.getDateCheckOut());
    reservation.setNumber(Long.valueOf(reservationDTO.hashCode()));

    return mapper.map(repository.save(reservation), ReservationDTO.class);
  }

  /**
   * Cancel a reservation
   *
   * @param numberReservation
   * @return void
   */
  @Override
  public void cancelAReservation(Long numberReservation) {
    var reservation = repository.findByNumber(numberReservation).orElseThrow(() ->
            new ApiException(HttpStatus.NOT_FOUND,
                    Messages.ERROR_RESERVATION_NOT_FOUND));
    reservation.setStatus(ReservationStatus.CANCELED);
    repository.save(reservation);
  }


  /**
   * find all Reservations
   *
   * @return List<ReservationDTO>
   */
  @Override
  public List<ReservationDTO> getAll() {
    var reservations = repository.findAll();
    return reservations.stream().map(reservation -> mapper.map(reservation, ReservationDTO.class))
            .collect(Collectors.toList());
  }

  /**
   * find hotel by id
   *
   * @param id
   * @return ReservationDTO
   */
  @Override
  public ReservationDTO findById(long id) {
    var reservation = repository.findById(id).orElseThrow(
            () -> new ApiException(HttpStatus.NOT_FOUND,
                    Messages.ERROR_RESERVATION_NOT_FOUND));
    return mapper.map(reservation, ReservationDTO.class);
  }
}
