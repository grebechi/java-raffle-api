package br.com.gabrielrebechi.raffle.repository;

import br.com.gabrielrebechi.raffle.model.Participant;
import br.com.gabrielrebechi.raffle.model.RaffleEntry;
import br.com.gabrielrebechi.raffle.model.RaffleGroup;
import br.com.gabrielrebechi.raffle.model.enumtype.DrawType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RaffleEntryRepository extends JpaRepository<RaffleEntry, UUID> {

    Optional<RaffleEntry> findByParticipantAndGroup(Participant participant, RaffleGroup group);

    List<RaffleEntry> findAllByGroupId(UUID groupId);

    @Query("""
    select re
    from RaffleEntry re
    where re.group.id = :groupId
      and re.id not in (
            select rw.raffleEntry.id
            from RaffleWinner rw
            join rw.raffleDraw rd
            where rd.group.id = :groupId
              and rd.drawType = :drawType
      )
""")
    List<RaffleEntry> findEligibleForDraw(@Param("groupId") UUID groupId, @Param("drawType") DrawType drawType);
}
