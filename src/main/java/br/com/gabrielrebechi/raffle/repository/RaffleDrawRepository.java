package br.com.gabrielrebechi.raffle.repository;

import br.com.gabrielrebechi.raffle.model.RaffleDraw;
import br.com.gabrielrebechi.raffle.model.RaffleGroup;
import br.com.gabrielrebechi.raffle.model.enumtype.DrawType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import java.util.UUID;

public interface RaffleDrawRepository extends JpaRepository<RaffleDraw, UUID> {

    List<RaffleDraw> findByGroup(RaffleGroup group);

    List<RaffleDraw> findByGroupAndDrawType(RaffleGroup group, DrawType drawType);

}
