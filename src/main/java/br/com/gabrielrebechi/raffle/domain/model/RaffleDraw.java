package br.com.gabrielrebechi.raffle.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "raffle_draw")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RaffleDraw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private RaffleGroup group;

    @Enumerated(EnumType.STRING)
    @Column(name = "draw_type", nullable = false)
    private DrawType drawType;

    @Column(name = "draw_date", nullable = false, updatable = false)
    private LocalDateTime drawDate;

    @PrePersist
    protected void onCreate() {
        this.drawDate = LocalDateTime.now();
    }
}
