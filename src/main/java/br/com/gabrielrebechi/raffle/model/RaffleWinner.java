package br.com.gabrielrebechi.raffle.model;

import br.com.gabrielrebechi.raffle.model.base.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(
        name = "raffle_winner",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"raffle_draw_id", "raffle_entry_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RaffleWinner extends Auditable {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "raffle_draw_id")
    private RaffleDraw raffleDraw;

    @ManyToOne(optional = false)
    @JoinColumn(name = "raffle_entry_id")
    private RaffleEntry raffleEntry;
}