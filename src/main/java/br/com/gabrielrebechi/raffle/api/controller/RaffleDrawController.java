package br.com.gabrielrebechi.raffle.api.controller;
import br.com.gabrielrebechi.raffle.api.dto.RaffleDrawRequest;
import br.com.gabrielrebechi.raffle.api.dto.RaffleWinnerResponse;
import br.com.gabrielrebechi.raffle.model.RaffleWinner;
import br.com.gabrielrebechi.raffle.service.RaffleDrawService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/draw")
@RequiredArgsConstructor
public class RaffleDrawController {

    private final RaffleDrawService raffleDrawService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RaffleWinnerResponse executeDraw(
            @RequestBody @Valid RaffleDrawRequest request
    ) {

        RaffleWinner winner = raffleDrawService.executeDraw(
                request.groupKeyword(),
                request.drawType()
        );

        return new RaffleWinnerResponse(
                winner.getRaffleDraw().getId(),
                winner.getRaffleEntry().getId(),
                winner.getRaffleEntry().getParticipant().getFullName()
        );
    }
}