package br.com.gabrielrebechi.raffle.domain.dto;


import java.time.LocalDateTime;

public record RaffleGroupResponse(
        Long id,
        String keyword,
        Long participantsCount,
        LocalDateTime createdAt
) {}
