package com.WaitingList.BACKEND.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VisitorRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;
} 