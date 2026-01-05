package br.com.gabrielrebechi.raffle.api.controller;

import br.com.gabrielrebechi.raffle.api.dto.CreateRaffleGroupRequest;
import br.com.gabrielrebechi.raffle.api.dto.RaffleGroupResponse;
import br.com.gabrielrebechi.raffle.model.RaffleGroup;
import br.com.gabrielrebechi.raffle.service.RaffleGroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class RaffleGroupController {

    private final RaffleGroupService raffleGroupService;

    @PostMapping
    public ResponseEntity<RaffleGroupResponse> create(
            @RequestBody @Valid CreateRaffleGroupRequest request
    ) {
        RaffleGroup group =
                raffleGroupService.create(
                        request.keyword(),
                        request.description()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toResponse(group));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<RaffleGroupResponse> getById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                toResponse(raffleGroupService.getById(id))
        );
    }

    @GetMapping("/{keyword}")
    public ResponseEntity<RaffleGroupResponse> getByKeyword(
            @PathVariable String keyword
    ) {
        return ResponseEntity.ok(
                toResponse(raffleGroupService.getByKeyword(keyword))
        );
    }

    @GetMapping
    public ResponseEntity<List<RaffleGroupResponse>> listAll() {
        return ResponseEntity.ok(
                raffleGroupService.listAll()
                        .stream()
                        .map(this::toResponse)
                        .toList()
        );
    }

    private RaffleGroupResponse toResponse(RaffleGroup group) {
        return new RaffleGroupResponse(
                group.getId(),
                group.getKeyword(),
                group.getDescription()
        );
    }
}