package br.com.gabrielrebechi.raffle.service;

import br.com.gabrielrebechi.raffle.domain.dto.RaffleWinnerResponse;
import br.com.gabrielrebechi.raffle.domain.model.*;
import br.com.gabrielrebechi.raffle.repository.ParticipantRepository;
import br.com.gabrielrebechi.raffle.repository.RaffleDrawRepository;
import br.com.gabrielrebechi.raffle.repository.RaffleWinnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RaffleService {

    private final RaffleGroupService raffleGroupService;
    private final ParticipantRepository participantRepository;
    private final RaffleDrawRepository raffleDrawRepository;
    private final RaffleWinnerRepository raffleWinnerRepository;
    private final Random random = new Random();

    @Transactional
    public RaffleWinnerResponse draw(String groupKeyword, boolean allowRepetition) {
        RaffleGroup group = raffleGroupService.findOrCreateByKeyword(groupKeyword);

        List<Participant> pool = participantRepository.findByGroupOrderByRegistrationNumberAsc(group);
        if (pool.isEmpty()) {
            throw new IllegalArgumentException("Não há participantes cadastrados no grupo '" + groupKeyword + "'.");
        }

        if (!allowRepetition) {
            pool = pool.stream()
                    .filter(p -> raffleWinnerRepository.countByParticipantAndDraw_GroupAndDraw_DrawType(
                            p, group, DrawType.WITHOUT_REPETITION) == 0)
                    .collect(Collectors.toList());

            if (pool.isEmpty()) {
                throw new IllegalStateException("Todos os participantes do grupo '" + groupKeyword + "' já foram sorteados.");
            }
        }

        Participant winnerParticipant = pool.get(random.nextInt(pool.size()));

        RaffleDraw draw = RaffleDraw.builder()
                .group(group)
                .drawType(allowRepetition ? DrawType.WITH_REPETITION : DrawType.WITHOUT_REPETITION)
                .build();
        raffleDrawRepository.save(draw);

        RaffleWinner winner = RaffleWinner.builder()
                .draw(draw)
                .participant(winnerParticipant)
                .build();
        raffleWinnerRepository.save(winner);

        return toResponse(winner);
    }


    @Transactional(readOnly = true)
    public List<RaffleWinnerResponse> listWinners(String groupKeyword) {
        RaffleGroup group = raffleGroupService.findByKeywordOrThrow(groupKeyword);

        List<RaffleWinner> winners = raffleWinnerRepository.findByDraw_GroupOrderByWonAtDesc(group);

         if (winners.isEmpty()) {
             throw new IllegalStateException("Não há vencedores cadastrados para o grupo '" + groupKeyword + "'.");
         }

        return winners.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private RaffleWinnerResponse toResponse(RaffleWinner winner) {
        Participant p = winner.getParticipant();
        RaffleDraw draw = winner.getDraw();

        return new RaffleWinnerResponse(
                p.getId().toString(),
                p.getFullName(),
                p.getEmail(),
                p.getGroup().getKeyword(),
                p.getRegistrationNumber(),
                p.getGlobalCounter(),
                p.getCreatedAt(),
                draw.getId(),
                draw.getDrawType().name(),
                winner.getWonAt()
        );
    }
}
