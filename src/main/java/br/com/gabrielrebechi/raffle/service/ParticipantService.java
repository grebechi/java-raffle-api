package br.com.gabrielrebechi.raffle.service;

import br.com.gabrielrebechi.raffle.domain.dto.CreateParticipantRequest;
import br.com.gabrielrebechi.raffle.domain.model.Participant;
import br.com.gabrielrebechi.raffle.repository.ParticipantRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final EntityManager entityManager;

    @Transactional
    public Participant create(CreateParticipantRequest request) {

        // validação
        if (participantRepository.existsByKeywordIgnoreCaseAndEmailIgnoreCase(request.keyword(), request.email())) {
            throw new IllegalArgumentException("Email já registrado para esta palavra-chave");
        }

        Participant participant = Participant.builder()
                .fullName(request.fullName())
                .email(request.email())
                .keyword(request.keyword())
                .build();

        Participant p = participantRepository.save(participant);
        participantRepository.flush();
        entityManager.refresh(p);

        return p;
    }
}
