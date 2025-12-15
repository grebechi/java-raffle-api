package br.com.gabrielrebechi.raffle.service;

import br.com.gabrielrebechi.raffle.domain.dto.CreateParticipantRequest;
import br.com.gabrielrebechi.raffle.domain.model.Participant;
import br.com.gabrielrebechi.raffle.domain.model.RaffleGroup;
import br.com.gabrielrebechi.raffle.repository.ParticipantRepository;
import br.com.gabrielrebechi.raffle.repository.RaffleWinnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final RaffleGroupService raffleGroupService;
    private final RaffleWinnerRepository raffleWinnerRepository;

    @Transactional
    public Participant create(CreateParticipantRequest request) {
        RaffleGroup group = raffleGroupService.findOrCreateByKeyword(request.keyword());

        if (participantRepository.existsByGroupAndEmailIgnoreCase(group, request.email())) {
            throw new IllegalArgumentException("E-mail já cadastrado neste grupo");
        }

        long globalCounter = participantRepository.count() + 1;
        long registrationNumber = participantRepository.countByGroup(group) + 1;

        Participant participant = Participant.builder()
                .fullName(request.fullName())
                .email(request.email())
                .group(group)
                .globalCounter(globalCounter)
                .registrationNumber(registrationNumber)
                .build();

        return participantRepository.save(participant);
    }

    @Transactional(readOnly = true)
    public List<Participant> findByGroupKeyword(String keyword) {
        RaffleGroup group = raffleGroupService.findByKeywordOrThrow(keyword);
        return participantRepository.findByGroupOrderByRegistrationNumberAsc(group);
    }

    @Transactional
    public void deleteById(UUID id) {
        Participant participant = participantRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Participante não encontrado"));

        boolean hasWinners = raffleWinnerRepository.existsByParticipant(participant);
        if (hasWinners) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Não é possível excluir participante que já participou de sorteios");
        }

        participantRepository.delete(participant);
    }

}
