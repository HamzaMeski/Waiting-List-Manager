package com.WaitingList.BACKEND.repository;

import com.WaitingList.BACKEND.entity.WaitingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface WaitingRoomRepository extends JpaRepository<WaitingRoom, Long> {
    Optional<WaitingRoom> findByDate(LocalDate date);
}