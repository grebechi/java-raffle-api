package br.com.gabrielrebechi.raffle.domain.dto;

import java.time.LocalDateTime;

public record RaffleDrawResponse(
        Long id,
        String groupKeyword,
        String drawType,
        LocalDateTime drawDate
) {}
