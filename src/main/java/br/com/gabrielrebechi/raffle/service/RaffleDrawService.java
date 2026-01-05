package br.com.gabrielrebechi.raffle.service;

import br.com.gabrielrebechi.raffle.model.RaffleDraw;
import br.com.gabrielrebechi.raffle.model.RaffleEntry;
import br.com.gabrielrebechi.raffle.model.RaffleGroup;
import br.com.gabrielrebechi.raffle.model.RaffleWinner;
import br.com.gabrielrebechi.raffle.model.enumtype.DrawType;
import br.com.gabrielrebechi.raffle.repository.RaffleDrawRepository;
import br.com.gabrielrebechi.raffle.repository.RaffleEntryRepository;
import br.com.gabrielrebechi.raffle.repository.RaffleWinnerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RaffleDrawService {

    private final RaffleDrawRepository raffleDrawRepository;
    private final RaffleEntryRepository raffleEntryRepository;
    private final RaffleWinnerRepository raffleWinnerRepository;
    private final RaffleGroupService raffleGroupService;

    private final SecureRandom random = new SecureRandom();

    @Transactional
    public RaffleWinner executeDraw(String groupKeyword, DrawType drawType) {

        // 1️⃣ Buscar grupo
        RaffleGroup group = raffleGroupService.getByKeyword(groupKeyword);

        // 2️⃣ Criar o sorteio
        RaffleDraw draw = raffleDrawRepository.save(
                RaffleDraw.builder()
                        .group(group)
                        .drawType(drawType)
                        .build()
        );

        // 3️⃣ Buscar inscrições elegíveis
        List<RaffleEntry> eligibleEntries =
                drawType == DrawType.BLOCK_REPEAT_WINNERS
                        ? raffleEntryRepository.findEligibleForDraw(group.getId(), drawType)
                        : raffleEntryRepository.findAllByGroupId(group.getId());

        if (eligibleEntries.isEmpty()) {
            throw new IllegalStateException("No eligible entries for this draw");
        }

        // 4️⃣ Sortear 1 vencedor
        RaffleEntry selected =
                eligibleEntries.get(random.nextInt(eligibleEntries.size()));

        // 5️⃣ Persistir vencedor
        return raffleWinnerRepository.save(
                RaffleWinner.builder()
                        .raffleDraw(draw)
                        .raffleEntry(selected)
                        .build()
        );
    }
}

