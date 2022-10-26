package com.alten.hotel.service;

import com.alten.hotel.dto.RoomDTO;
import com.alten.hotel.enumaration.RoomStatus;
import com.alten.hotel.exception.ApiException;
import com.alten.hotel.model.Room;
import com.alten.hotel.repository.RoomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoomServiceImplTest {


  @InjectMocks
  private RoomServiceImpl roomService;
  @Mock
  private RoomRepository repository;
  @Mock
  private ModelMapper mapper;

  private RoomDTO roomDTO;

  private Room room;
  private List<RoomDTO> roomDTOS;
  private List<Room> rooms;

  @BeforeEach
  void setUp() {
    setRoom();
    setRoomDTO();
    roomDTOS = new ArrayList<>();
    roomDTOS.add(roomDTO);

    rooms = new ArrayList<>();
    rooms.add(room);
  }
  @Test
  void getAll() {
    when(repository.findAll()).thenReturn(rooms);

    List<RoomDTO> roomDTOS = roomService.getAll();
    Mockito.verify(repository).findAll();
    Mockito.verify(mapper).map(rooms.get(0), RoomDTO.class);
    assertNotNull(roomDTOS);
  }

  @Test
  void getAllAvailableRooms() {
    RoomStatus roomStatus = RoomStatus.READY_TO_RESERVE;

    when(repository.findByStatus(roomStatus)).thenReturn(rooms);

    List<RoomDTO> roomDTOS = roomService.getAllAvailableRooms(roomStatus);
    Mockito.verify(repository).findByStatus(roomStatus);
    Mockito.verify(mapper).map(rooms.get(0), RoomDTO.class);
    assertNotNull(roomDTOS);
  }

  @Test
  void findByIdSuccess() {
    long id = 1L;
    when(repository.findById(id)).thenReturn(Optional.of(room));

    roomService.findById(id);

    verify(repository).findById(id);
    verify(mapper).map(room, RoomDTO.class);

  }

  @Test
  void findByIdNotfound() {
    long id = 2L;
    when(repository.findById(id)).thenReturn(Optional.empty());
    Assertions.assertThrows(ApiException.class, () -> roomService.findById(id));
  }

  @Test
  void checkAvailabilitySuccess() {
    long id = 1L;
    when(repository.findById(id)).thenReturn(Optional.of(room));
    when(mapper.map(room, RoomDTO.class)).thenReturn(roomDTO);
    roomService.checkAvailability(id);

    verify(repository).findById(id);
    verify(mapper).map(room, RoomDTO.class);

  }

  @Test
  void checkAvailabilityNotFoundERROR() {
    long id = 1L;
    when(repository.findById(id)).thenReturn(Optional.empty());
    Assertions.assertThrows(ApiException.class, () -> roomService.checkAvailability(id));
  }


  private void setRoom(){
    room = new Room();
    room.setId(1L);
    room.setNumber(10L);
    room.setStatus(RoomStatus.READY_TO_RESERVE);
  }

  private void setRoomDTO(){
    roomDTO = new RoomDTO();
    roomDTO.setId(1L);
    roomDTO.setNumber(10L);
    roomDTO.setStatus(RoomStatus.READY_TO_RESERVE);
  }
}