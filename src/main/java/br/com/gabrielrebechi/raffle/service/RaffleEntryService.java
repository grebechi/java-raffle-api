package br.com.gabrielrebechi.raffle.service;

import br.com.gabrielrebechi.raffle.api.dto.RaffleEntryRequest;
import br.com.gabrielrebechi.raffle.api.dto.RaffleEntryResponse;
import br.com.gabrielrebechi.raffle.model.Participant;
import br.com.gabrielrebechi.raffle.model.RaffleEntry;
import br.com.gabrielrebechi.raffle.model.RaffleGroup;
import br.com.gabrielrebechi.raffle.repository.RaffleEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RaffleEntryService {

    private final ParticipantService participantService;
    private final RaffleGroupService raffleGroupService;
    private final RaffleEntryRepository raffleEntryRepository;

    @Transactional
    public RaffleEntryResponse createEntry(RaffleEntryRequest request) {

        // 1️⃣ Buscar grupo pelo keyword
        RaffleGroup group = raffleGroupService.getByKeyword(
                request.getGroupKeyword()
        );

        // 2️⃣ Buscar ou criar participante
        Participant participant =
                participantService.getOrCreate(
                        request.getFullName(),
                        request.getEmail()
                );

        // 3️⃣ Verificar se já existe inscrição no grupo
        raffleEntryRepository.findByParticipantAndGroup(participant, group)
                .ifPresent(entry -> {
                    throw new IllegalStateException("Participant already registered in this group");
                });

        // 4️⃣ Criar inscrição
        RaffleEntry entry = raffleEntryRepository.save(
                RaffleEntry.builder()
                        .participant(participant)
                        .group(group)
                        .build()
        );

        // 5️⃣ Retornar DTO rico (sem nova consulta)
        return new RaffleEntryResponse(
                entry.getId(),

                participant.getId(),
                participant.getFullName(),
                participant.getEmail(),

                group.getId(),
                group.getKeyword(),
                group.getDescription(),

                entry.getCreatedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<RaffleEntryResponse> listByGroupKeyword(String groupKeyword) {

        RaffleGroup group = raffleGroupService.getByKeyword(groupKeyword);

        return raffleEntryRepository.findAllByGroupId(group.getId())
                .stream()
                .map(entry -> new RaffleEntryResponse(
                        entry.getId(),

                        entry.getParticipant().getId(),
                        entry.getParticipant().getFullName(),
                        entry.getParticipant().getEmail(),

                        group.getId(),
                        group.getKeyword(),
                        group.getDescription(),

                        entry.getCreatedAt()
                ))
                .toList();
    }
}