package br.com.gabrielrebechi.raffle.service;

import br.com.gabrielrebechi.raffle.domain.dto.CreateParticipantRequest;
import br.com.gabrielrebechi.raffle.domain.model.Participant;
import br.com.gabrielrebechi.raffle.domain.model.RaffleGroup;
import br.com.gabrielrebechi.raffle.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final RaffleGroupService raffleGroupService;

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
        RaffleGroup group = raffleGroupService.findOrCreateByKeyword(keyword);
        return participantRepository.findByGroupOrderByRegistrationNumberAsc(group);
    }
}
