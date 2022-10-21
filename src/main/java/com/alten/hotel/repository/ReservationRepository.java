package com.alten.hotel.repository;

import com.alten.hotel.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {




  @Query("SELECT r FROM Reservation r WHERE :date between r.dateCheckIn and r.dateCheckOut")
  Optional<Reservation> findReservationByDateBetweenPeriod(LocalDate date);
}
