package br.com.gabrielrebechi.raffle.repository;

import br.com.gabrielrebechi.raffle.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ParticipantRepository extends JpaRepository<Participant, UUID> {

    Optional<Participant> findByEmail(String email);

    boolean existsByEmail(String email);
}