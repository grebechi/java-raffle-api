package br.com.gabrielrebechi.raffle.repository;

import br.com.gabrielrebechi.raffle.domain.model.RaffleGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RaffleGroupRepository extends JpaRepository<RaffleGroup, Long> {

    Optional<RaffleGroup> findByKeywordIgnoreCase(String keyword);
    boolean existsByKeywordIgnoreCase(String keyword);
}
