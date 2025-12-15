package br.com.gabrielrebechi.raffle.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateParticipantRequest(

        @NotBlank
        String fullName,

        @NotBlank
        String email,

        @NotBlank
        String keyword

) {}