package br.com.gabrielrebechi.raffle.api.dto;

import br.com.gabrielrebechi.raffle.model.enumtype.DrawType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RaffleDrawRequest(

        @NotBlank
        String groupKeyword,

        @NotNull
        DrawType drawType

) {}
