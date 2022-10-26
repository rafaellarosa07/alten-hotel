package com.alten.hotel.repository;

import com.alten.hotel.enumaration.RoomStatus;
import com.alten.hotel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

  List<Room> findByStatus(RoomStatus status);

}
