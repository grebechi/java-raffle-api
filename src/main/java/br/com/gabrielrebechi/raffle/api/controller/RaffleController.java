package br.com.gabrielrebechi.raffle.api.controller;

import br.com.gabrielrebechi.raffle.domain.dto.CreateRaffleDrawRequest;
import br.com.gabrielrebechi.raffle.domain.dto.RaffleWinnerResponse;
import br.com.gabrielrebechi.raffle.service.RaffleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/raffles")
@RequiredArgsConstructor
public class RaffleController {

    private final RaffleService raffleService;

    @PostMapping("/draw")
    @ResponseStatus(HttpStatus.CREATED)
    public RaffleWinnerResponse draw(@RequestBody @Valid CreateRaffleDrawRequest request) {
        return raffleService.draw(request.groupKeyword(), request.allowRepetition());
    }

    @GetMapping("/winners")
    public List<RaffleWinnerResponse> listWinners(@RequestParam String groupKeyword) {
        return raffleService.listWinners(groupKeyword);
    }
}
