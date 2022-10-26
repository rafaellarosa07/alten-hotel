package com.alten.hotel.service;

import com.alten.hotel.dto.ReservationDTO;
import com.alten.hotel.dto.RoomDTO;
import com.alten.hotel.dto.UserViewDTO;
import com.alten.hotel.enumaration.ReservationStatus;
import com.alten.hotel.enumaration.RoomStatus;
import com.alten.hotel.exception.ApiException;
import com.alten.hotel.model.Reservation;
import com.alten.hotel.model.Room;
import com.alten.hotel.model.User;
import com.alten.hotel.repository.ReservationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

  @InjectMocks
  private ReservationServiceImpl reservationService;
  @Mock
  private ReservationRepository repository;

  @Mock
  private RoomService roomService;
  @Mock
  private ModelMapper mapper;

  private ReservationDTO reservationDTO;

  private Reservation reservation;
  private List<ReservationDTO> reservationDTOS;
  private List<Reservation> reservations;



  @BeforeEach
  void setUp() {
    setReservation();
    setReservationDTO();
    reservationDTOS = new ArrayList<>();
    reservationDTOS.add(reservationDTO);

    reservations = new ArrayList<>();
    reservations.add(reservation);
  }
  @Test
  void reserveARoom() {
    var reserveDTO = reservationDTO;
    when(roomService.checkAvailability(reserveDTO.getRoom().getId())).thenReturn(RoomStatus.READY_TO_RESERVE);
    when(repository.findReservationByDateBetweenPeriod(reserveDTO.getDateCheckIn()
            .toLocalDate())).thenReturn(Optional.empty());
    when(mapper.map(any(), eq(Reservation.class))).thenReturn(reservation);
    when(mapper.map(any(), eq(ReservationDTO.class))).thenReturn(reserveDTO);

    var response = reservationService.reserveARoom(reserveDTO);

    assertEquals(response, reserveDTO);

  }

  @Test
  void reserveARoomExceptionErrorRoomAvailabilityByDate() {
    var reserveDTO = reservationDTO;
    when(roomService.checkAvailability(reserveDTO.getRoom().getId())).thenReturn(RoomStatus.READY_TO_RESERVE);
    when(repository.findReservationByDateBetweenPeriod(reserveDTO.getDateCheckIn()
            .toLocalDate())).thenReturn(Optional.of(reservation));

    Assertions.assertThrows(ApiException.class, () -> reservationService.reserveARoom(reserveDTO));
  }

  @Test
  void reserveARoomExceptionErrorRoomAvailability() {
    var reserveDTO = reservationDTO;
    when(roomService.checkAvailability(reserveDTO.getRoom().getId())).thenReturn(RoomStatus.INTERDICTED);

    Assertions.assertThrows(ApiException.class, () -> reservationService.reserveARoom(reserveDTO));
  }

  @Test
  void reserveARoomExceptionCheckStay() {
    var reserveDTO = reservationDTO;
    reserveDTO.setDateCheckIn(LocalDateTime.of(LocalDate.now(), LocalTime.now()));

    when(roomService.checkAvailability(reserveDTO.getRoom().getId())).thenReturn(RoomStatus.READY_TO_RESERVE);
    when(repository.findReservationByDateBetweenPeriod(reserveDTO.getDateCheckIn()
            .toLocalDate())).thenReturn(Optional.empty());

    Assertions.assertThrows(ApiException.class, () -> reservationService.reserveARoom(reserveDTO));


  }


  @Test
  void reserveARoomExceptionCheckNumberOfDays() {
    var reserveDTO = reservationDTO;
    reserveDTO.setDateCheckIn(reserveDTO.getDateCheckIn().minusDays(5));

    when(roomService.checkAvailability(reserveDTO.getRoom().getId())).thenReturn(RoomStatus.READY_TO_RESERVE);
    when(repository.findReservationByDateBetweenPeriod(reserveDTO.getDateCheckIn()
            .toLocalDate())).thenReturn(Optional.empty());

    Assertions.assertThrows(ApiException.class, () -> reservationService.reserveARoom(reserveDTO));


  }

  @Test
  void reserveARoomExceptionCheckNumberOfDaysAdvance() {
    var checkIn = LocalDate.now().plusDays(31);
    var checkOut = LocalDate.now().plusDays(33);
    var reserveDTO = reservationDTO;
    reserveDTO.setDateCheckIn(LocalDateTime.of(checkIn, LocalTime.now()));
    reserveDTO.setDateCheckOut(LocalDateTime.of(checkOut, LocalTime.now()));

    when(roomService.checkAvailability(reserveDTO.getRoom().getId())).thenReturn(RoomStatus.READY_TO_RESERVE);
    when(repository.findReservationByDateBetweenPeriod(reserveDTO.getDateCheckIn()
            .toLocalDate())).thenReturn(Optional.empty());

    Assertions.assertThrows(ApiException.class, () -> reservationService.reserveARoom(reserveDTO));


  }

  @Test
  void modifyAReservation() {
    var reserveDTO = reservationDTO;
    when(repository.findById(reserveDTO.getId())).thenReturn(Optional.of(reservation));
    when(roomService.checkAvailability(reserveDTO.getRoom().getId())).thenReturn(RoomStatus.READY_TO_RESERVE);
    when(repository.findReservationByDateBetweenPeriod(reserveDTO.getDateCheckIn()
            .toLocalDate())).thenReturn(Optional.empty());
    when(mapper.map(any(), eq(ReservationDTO.class))).thenReturn(reserveDTO);
    when(mapper.map(any(), eq(Room.class))).thenReturn(reservation.getRoom());


    var response = reservationService.modifyAReservation(reserveDTO);

    assertEquals(response, reserveDTO);
  }


  @Test
  void modifyAReservationExceptionNotFound() {
    var reserveDTO = reservationDTO;
    when(repository.findById(reserveDTO.getId())).thenReturn(Optional.empty());
    when(roomService.checkAvailability(reserveDTO.getRoom().getId())).thenReturn(RoomStatus.READY_TO_RESERVE);
    when(repository.findReservationByDateBetweenPeriod(reserveDTO.getDateCheckIn()
            .toLocalDate())).thenReturn(Optional.empty());

    Assertions.assertThrows(ApiException.class, () -> reservationService.modifyAReservation(reserveDTO));
  }

  @Test
  void cancelAReservation() {
    Long numberReservation = 123123L;

    when(repository.findByNumber(numberReservation)).thenReturn(Optional.of(reservation));

    reservationService.cancelAReservation(numberReservation);

    verify(repository).findByNumber(numberReservation);
    verify(repository).save(reservation);
  }

  @Test
  void cancelAReservationExceptionNoutFound() {
    Long numberReservation = 123123L;

    when(repository.findByNumber(numberReservation)).thenReturn(Optional.empty());

    Assertions.assertThrows(ApiException.class, () -> reservationService.cancelAReservation(numberReservation));
  }

  @Test
  void getAll() {
    when(repository.findAll()).thenReturn(reservations);

    List<ReservationDTO> reservationDTOList = reservationService.getAll();
    Mockito.verify(repository).findAll();
    Mockito.verify(mapper).map(reservations.get(0), ReservationDTO.class);
    assertNotNull(reservationDTOList);
  }

  @Test
  void findByIdSuccess() {
    long id = 1L;
    when(repository.findById(id)).thenReturn(Optional.of(reservation));
    when(mapper.map(reservation, ReservationDTO.class)).thenReturn(reservationDTO);


    reservationService.findById(id);

    verify(repository).findById(id);
    verify(mapper).map(reservation, ReservationDTO.class);

  }

  @Test
  void findByIdNotfound() {
    long id = 2L;
    when(repository.findById(id)).thenReturn(Optional.empty());
    Assertions.assertThrows(ApiException.class, () -> reservationService.findById(id));
  }

  private void setReservationDTO() {
    var checkIn = LocalDate.now().plusDays(10);
    var checkOut = LocalDate.now().plusDays(12);
    reservationDTO = new ReservationDTO(1L, 123123L,
            LocalDateTime.of(checkIn,
                    LocalTime.of(12, 12)),
            LocalDateTime.of(checkOut,
                    LocalTime.of(12, 12)), new UserViewDTO(1L, "TESTE"),
            new RoomDTO(3L, 30L, RoomStatus.READY_TO_RESERVE), ReservationStatus.RESERVED);
  }

  private void setReservation() {
    var checkIn = LocalDate.now().plusDays(10);
    var checkOut = LocalDate.now().plusDays(12);
    reservation = new Reservation(1L, 123123L,
            LocalDateTime.of(checkIn,
                    LocalTime.of(12, 12)),
            LocalDateTime.of(checkOut,
                    LocalTime.of(12, 12)), new User(1L, "TESTE"),
            new Room(3L, 30L, RoomStatus.READY_TO_RESERVE), ReservationStatus.RESERVED);
  }
}