package br.com.gabrielrebechi.raffle.repository;

import br.com.gabrielrebechi.raffle.model.RaffleDraw;
import br.com.gabrielrebechi.raffle.model.RaffleEntry;
import br.com.gabrielrebechi.raffle.model.RaffleGroup;
import br.com.gabrielrebechi.raffle.model.RaffleWinner;
import br.com.gabrielrebechi.raffle.model.enumtype.DrawType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RaffleWinnerRepository extends JpaRepository<RaffleWinner, UUID> {

    List<RaffleWinner> findByRaffleDraw(RaffleDraw raffleDraw);

    boolean existsByRaffleEntryAndRaffleDraw(RaffleEntry raffleEntry, RaffleDraw raffleDraw);

    @Query("""
        select case when count(rw) > 0 then true else false end
        from RaffleWinner rw
        join rw.raffleDraw rd
        where rw.raffleEntry = :entry
          and rd.group = :group
          and rd.drawType = :drawType
    """)
    boolean hasEntryWonInGroupByDrawType(
            @Param("entry") RaffleEntry entry,
            @Param("group") RaffleGroup group,
            @Param("drawType") DrawType drawType
    );
}
