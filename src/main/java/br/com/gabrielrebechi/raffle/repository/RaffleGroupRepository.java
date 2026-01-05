package br.com.gabrielrebechi.raffle.repository;

import br.com.gabrielrebechi.raffle.model.RaffleGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RaffleGroupRepository extends JpaRepository<RaffleGroup, UUID> {

    Optional<RaffleGroup> findByKeywordIgnoreCase(String keyword);
    boolean existsByKeywordIgnoreCase(String keyword);
}
