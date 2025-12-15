package br.com.gabrielrebechi.raffle.repository;

import br.com.gabrielrebechi.raffle.domain.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ParticipantRepository extends JpaRepository<Participant, UUID> {

    List<Participant> findAllByKeywordIgnoreCase(String keyword);
    boolean existsByKeywordIgnoreCaseAndEmailIgnoreCase(String keyword, String email);

}