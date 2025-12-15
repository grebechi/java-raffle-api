package br.com.gabrielrebechi.raffle.repository;

import br.com.gabrielrebechi.raffle.domain.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RaffleWinnerRepository extends JpaRepository<RaffleWinner, Long> {

    List<RaffleWinner> findByDraw(RaffleDraw draw);

    long countByParticipantAndDraw_GroupAndDraw_DrawType(
            Participant participant,
            RaffleGroup group,
            DrawType drawType
    );

    List<RaffleWinner> findByDraw_GroupOrderByWonAtDesc(RaffleGroup group);
}
