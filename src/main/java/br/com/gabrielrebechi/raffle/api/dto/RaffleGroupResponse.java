package br.com.gabrielrebechi.raffle.api.dto;

import java.util.UUID;

public record RaffleGroupResponse(
        UUID id,
        String keyword,
        String description
) {}
