package com.colvir.szasemkov.homework1.dto;

import com.colvir.szasemkov.homework1.model.InternalErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private InternalErrorStatus status;

    private String message;
}
