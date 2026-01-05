package br.com.gabrielrebechi.raffle.model;

import br.com.gabrielrebechi.raffle.model.base.Auditable;
import br.com.gabrielrebechi.raffle.model.enumtype.DrawType;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "raffle_draw")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RaffleDraw extends Auditable {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "raffle_group_id", nullable = false)
    private RaffleGroup group;

    @Enumerated(EnumType.STRING)
    @Column(name = "draw_type", nullable = false)
    private DrawType drawType;

}
