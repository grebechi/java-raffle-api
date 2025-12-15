package br.com.gabrielrebechi.raffle.domain.dto;

import java.time.LocalDateTime;

public record ParticipantResponse(
        String id,
        String fullName,
        String email,
        String groupKeyword,
        Long registrationNumber,
        Long globalCounter,
        LocalDateTime createdAt
) {}

