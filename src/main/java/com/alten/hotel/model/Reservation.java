package com.alten.hotel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "RESERVATION")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "NUMBER", nullable = false)
  private Long number;

  @Column(name = "DATE_CHECK_IN", nullable = false)
  private LocalDateTime dateCheckIn;

  @Column(name = "DATE_CHECK_OUT", nullable = false)
  private LocalDateTime dateCheckOut;

  @ManyToOne
  @JoinColumn(name = "USER_ID", nullable = false)
  private User user;

  @OneToOne
  @JoinColumn(name = "ROOM_ID", nullable = false)
  private Room room;

  @Column(name = "IS_CANCELED", nullable = false)
  private Boolean isCanceled;

  @Column(name = "LAST_MODIFICATION", nullable = false)
  private LocalDateTime lastModification;

}
