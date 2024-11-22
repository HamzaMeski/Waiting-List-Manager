package com.WaitingList.BACKEND.repository;

import com.WaitingList.BACKEND.entity.WaitingRoomStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaitingRoomStatisticsRepository extends JpaRepository<WaitingRoomStatistics, Long> {
    // The ID will be the same as the waiting room ID
}