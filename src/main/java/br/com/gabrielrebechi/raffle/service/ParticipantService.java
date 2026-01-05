package br.com.gabrielrebechi.raffle.service;

import br.com.gabrielrebechi.raffle.model.Participant;
import br.com.gabrielrebechi.raffle.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantRepository participantRepository;

    @Transactional
    public Participant getOrCreate(String fullName, String email) {

        return participantRepository.findByEmail(email)
                .map(existing -> {
                    // decisão de negócio: atualizar nome
                    existing.setFullName(fullName);
                    return existing;
                })
                .orElseGet(() ->
                        participantRepository.save(
                                Participant.builder()
                                        .fullName(fullName)
                                        .email(email)
                                        .build()
                        )
                );
    }
}
