package br.com.gabrielrebechi.raffle.repository;

import br.com.gabrielrebechi.raffle.domain.model.RaffleDraw;
import br.com.gabrielrebechi.raffle.domain.model.RaffleGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RaffleDrawRepository extends JpaRepository<RaffleDraw, Long> {

    List<RaffleDraw> findByGroupOrderByDrawDateDesc(RaffleGroup group);
}
