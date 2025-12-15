package br.com.gabrielrebechi.raffle.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateRaffleGroupRequest(

        @NotBlank
        String keyword

) {}
