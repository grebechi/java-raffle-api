package br.com.gabrielrebechi.raffle.api.controller;

import br.com.gabrielrebechi.raffle.domain.dto.CreateParticipantRequest;
import br.com.gabrielrebechi.raffle.domain.dto.ParticipantResponse;
import br.com.gabrielrebechi.raffle.domain.model.Participant;
import br.com.gabrielrebechi.raffle.service.ParticipantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/participants")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantService participantService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipantResponse create(@RequestBody @Valid CreateParticipantRequest request) {
        var participant = participantService.create(request);
        return toResponse(participant);
    }

    @GetMapping("/{keyword}")
    public List<ParticipantResponse> getByGroup(@PathVariable String keyword) {
        var participants = participantService.findByGroupKeyword(keyword);
        return participants.stream().map(this::toResponse).toList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteParticipant(@PathVariable UUID id) {
        participantService.deleteById(id);
    }

    private ParticipantResponse toResponse(Participant participant) {
        return new ParticipantResponse(
                participant.getId().toString(),
                participant.getFullName(),
                participant.getEmail(),
                participant.getGroup().getKeyword(),
                participant.getRegistrationNumber(),
                participant.getGlobalCounter(),
                participant.getCreatedAt()
        );
    }
}
