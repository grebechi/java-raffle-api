package br.com.gabrielrebechi.raffle.api.controller;

import br.com.gabrielrebechi.raffle.domain.dto.CreateRaffleGroupRequest;
import br.com.gabrielrebechi.raffle.domain.dto.RaffleGroupResponse;
import br.com.gabrielrebechi.raffle.domain.model.RaffleGroup;
import br.com.gabrielrebechi.raffle.repository.ParticipantRepository;
import br.com.gabrielrebechi.raffle.service.RaffleGroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class RaffleGroupController {

    private final RaffleGroupService raffleGroupService;
    private final ParticipantRepository participantRepository;

    @GetMapping
    public List<RaffleGroupResponse> listAll() {
        return raffleGroupService.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public RaffleGroupResponse getById(@PathVariable Long id) {
        return toResponse(raffleGroupService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RaffleGroupResponse create(@RequestBody @Valid CreateRaffleGroupRequest request) {
        RaffleGroup group = raffleGroupService.create(request);
        return toResponse(group);
    }

    @PutMapping("/{id}")
    public RaffleGroupResponse update(@PathVariable Long id,
                                      @RequestBody @Valid CreateRaffleGroupRequest request) {
        RaffleGroup group = raffleGroupService.update(id, request);
        return toResponse(group);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        raffleGroupService.delete(id);
    }

    private RaffleGroupResponse toResponse(RaffleGroup group) {
        long participantsCount = participantRepository.countByGroup(group);
        return new RaffleGroupResponse(
                group.getId(),
                group.getKeyword(),
                participantsCount,
                group.getCreatedAt()
        );
    }
}
