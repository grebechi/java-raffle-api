package br.com.gabrielrebechi.raffle.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateRaffleDrawRequest(
        @NotBlank String groupKeyword,
        Boolean allowRepetition
) {
    public CreateRaffleDrawRequest(String groupKeyword) {
        this(groupKeyword, false);
    }
}
