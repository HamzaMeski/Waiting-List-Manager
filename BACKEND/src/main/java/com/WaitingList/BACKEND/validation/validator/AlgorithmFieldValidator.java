package com.WaitingList.BACKEND.validation.validator;

import com.WaitingList.BACKEND.dto.request.visit.VisitRequestDTO;
import com.WaitingList.BACKEND.entity.WaitingRoom;
import com.WaitingList.BACKEND.repository.WaitingRoomRepository;
import com.WaitingList.BACKEND.util.constants.SchedulingAlgorithm;
import com.WaitingList.BACKEND.validation.annotation.AlgorithmFieldValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AlgorithmFieldValidator implements ConstraintValidator<AlgorithmFieldValidation, VisitRequestDTO> {
    private final WaitingRoomRepository waitingRoomRepository;

    @Override
    public boolean isValid(VisitRequestDTO visitRequest, ConstraintValidatorContext context) {
        if (visitRequest.getWaitingRoomId() == null) {
            return false;
        }

        WaitingRoom waitingRoom = waitingRoomRepository.findById(visitRequest.getWaitingRoomId())
                .orElse(null);
        
        if (waitingRoom == null) {
            return false;
        }

        SchedulingAlgorithm algorithm = waitingRoom.getAlgorithm();
        
        context.disableDefaultConstraintViolation();

        switch (algorithm) {
            case FIFO:
                if (visitRequest.getPriority() != null || visitRequest.getEstimatedProcessingTime() != null) {
                    context.buildConstraintViolationWithTemplate(
                        "Priority and estimated processing time should not be set for FIFO algorithm"
                    ).addConstraintViolation();
                    return false;
                }
                break;

            case PRIORITY:
                if (visitRequest.getPriority() == null) {
                    context.buildConstraintViolationWithTemplate(
                        "Priority is required for PRIORITY algorithm"
                    ).addConstraintViolation();
                    return false;
                }
                if (visitRequest.getEstimatedProcessingTime() != null) {
                    context.buildConstraintViolationWithTemplate(
                        "Estimated processing time should not be set for PRIORITY algorithm"
                    ).addConstraintViolation();
                    return false;
                }
                break;

            case SJF:
                if (visitRequest.getEstimatedProcessingTime() == null) {
                    context.buildConstraintViolationWithTemplate(
                        "Estimated processing time is required for SJF algorithm"
                    ).addConstraintViolation();
                    return false;
                }
                if (visitRequest.getPriority() != null) {
                    context.buildConstraintViolationWithTemplate(
                        "Priority should not be set for SJF algorithm"
                    ).addConstraintViolation();
                    return false;
                }
                break;
        }

        return true;
    }
} 