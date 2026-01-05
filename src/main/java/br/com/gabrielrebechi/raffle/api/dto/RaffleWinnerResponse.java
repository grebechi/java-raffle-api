package br.com.gabrielrebechi.raffle.api.dto;

import java.util.UUID;

public record RaffleWinnerResponse(
        UUID drawId,
        UUID entryId,
        String participantName
) {
}
