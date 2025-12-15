package br.com.gabrielrebechi.raffle.api.controller;

import br.com.gabrielrebechi.raffle.domain.dto.CreateRaffleDrawRequest;
import br.com.gabrielrebechi.raffle.domain.dto.RaffleWinnerResponse;
import br.com.gabrielrebechi.raffle.domain.dto.ErrorResponse;
import br.com.gabrielrebechi.raffle.service.RaffleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/raffles")
@RequiredArgsConstructor
public class RaffleController {

    private final RaffleService raffleService;

    @PostMapping("/draw")
    public ResponseEntity<?> draw(@RequestBody @Valid CreateRaffleDrawRequest request, HttpServletRequest httpRequest) {
        try {
            RaffleWinnerResponse winner = raffleService.draw(request.groupKeyword(), request.allowRepetition());
            return ResponseEntity.status(HttpStatus.CREATED).body(winner);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(
                            LocalDateTime.now(),
                            400,
                            "Bad Request",
                            e.getMessage(),
                            httpRequest.getRequestURI()
                    ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(
                            LocalDateTime.now(),
                            500,
                            "Internal Server Error",
                            e.getMessage(),
                            httpRequest.getRequestURI()
                    ));
        }
    }

    @GetMapping("/winners")
    public ResponseEntity<?> listWinners(@RequestParam String groupKeyword, HttpServletRequest httpRequest) {
        try {
            List<RaffleWinnerResponse> winners = raffleService.listWinners(groupKeyword);
            return ResponseEntity.ok(winners);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(
                            LocalDateTime.now(),
                            400,
                            "Bad Request",
                            e.getMessage(),
                            httpRequest.getRequestURI()
                    ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(
                            LocalDateTime.now(),
                            500,
                            "Internal Server Error",
                            e.getMessage(),
                            httpRequest.getRequestURI()
                    ));
        }
    }
}
