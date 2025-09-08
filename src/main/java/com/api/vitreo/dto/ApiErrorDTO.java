package com.api.vitreo.dto;

import java.time.Instant;

public record ApiErrorDTO(
        Instant timestamp,
        Integer status,
        String error,
        String message,
        String path
) {}