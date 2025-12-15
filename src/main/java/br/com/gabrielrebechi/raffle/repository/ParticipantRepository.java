package br.com.gabrielrebechi.raffle.repository;

import br.com.gabrielrebechi.raffle.domain.model.Participant;
import br.com.gabrielrebechi.raffle.domain.model.RaffleGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ParticipantRepository extends JpaRepository<Participant, UUID> {

    long count();
    long countByGroup(RaffleGroup group);
    boolean existsByGroupAndEmailIgnoreCase(RaffleGroup group, String email);
    List<Participant> findByGroupOrderByRegistrationNumberAsc(RaffleGroup group);
}