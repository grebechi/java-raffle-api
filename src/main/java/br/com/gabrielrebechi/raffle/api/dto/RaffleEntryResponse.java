package br.com.gabrielrebechi.raffle.api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record RaffleEntryResponse(
        UUID raffleEntryId,

        UUID participantId,
        String participantName,
        String participantEmail,

        UUID raffleGroupId,
        String groupKeyword,
        String groupDescription,

        LocalDateTime createdAt
) {}
