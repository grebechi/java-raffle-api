package br.com.gabrielrebechi.raffle.model;

import br.com.gabrielrebechi.raffle.model.base.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(
        name = "raffle_entry",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"participant_id", "raffle_group_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RaffleEntry extends Auditable {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "participant_id")
    private Participant participant;

    @ManyToOne(optional = false)
    @JoinColumn(name = "raffle_group_id")
    private RaffleGroup group;
}
