package br.com.gabrielrebechi.raffle.api.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateRaffleGroupRequest(

        @NotBlank
        String keyword,

        @NotBlank
        String description
) {}
