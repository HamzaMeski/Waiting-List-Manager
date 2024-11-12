package com.WaitingList.BACKEND.repository;

import com.WaitingList.BACKEND.entity.Visit;
import com.WaitingList.BACKEND.util.constants.VisitorStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    // For queue management
    List<Visit> findByWaitingRoomIdOrderByArrivalTimeAsc(Long waitingRoomId);
    List<Visit> findByWaitingRoomIdOrderByPriorityDescArrivalTimeAsc(Long waitingRoomId);
    List<Visit> findByWaitingRoomIdOrderByEstimatedProcessingTimeAscArrivalTimeAsc(Long waitingRoomId);

    // For capacity checking
    List<Visit> findByWaitingRoomIdAndStatus(Long waitingRoomId, VisitorStatus status);

    // For current visit
    Optional<Visit> findByVisitorIdAndStatusIn(Long visitorId, List<VisitorStatus> statuses);
}