package br.com.gabrielrebechi.raffle.domain.dto;

import java.time.LocalDateTime;

public record RaffleWinnerResponse(
        String id,
        String fullName,
        String email,
        String groupKeyword,
        Long registrationNumber,
        Long globalCounter,
        LocalDateTime participantCreatedAt,
        Long drawId,
        String drawType,
        LocalDateTime wonAt
) {}
