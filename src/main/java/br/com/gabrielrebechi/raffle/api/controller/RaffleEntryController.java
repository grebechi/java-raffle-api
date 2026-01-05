package br.com.gabrielrebechi.raffle.api.controller;

import br.com.gabrielrebechi.raffle.api.dto.RaffleEntryRequest;
import br.com.gabrielrebechi.raffle.api.dto.RaffleEntryResponse;
import br.com.gabrielrebechi.raffle.service.RaffleEntryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entry")
@RequiredArgsConstructor
public class RaffleEntryController {

    private final RaffleEntryService raffleEntryService;

    @PostMapping
    public ResponseEntity<RaffleEntryResponse> create(
            @RequestBody @Valid RaffleEntryRequest request
    ) {
        RaffleEntryResponse response =
                raffleEntryService.createEntry(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    @GetMapping("/group/{keyword}")
    public ResponseEntity<List<RaffleEntryResponse>> listByGroup(
            @PathVariable String keyword
    ) {
        List<RaffleEntryResponse> entries =
                raffleEntryService.listByGroupKeyword(keyword);

        return ResponseEntity.ok(entries);
    }
}