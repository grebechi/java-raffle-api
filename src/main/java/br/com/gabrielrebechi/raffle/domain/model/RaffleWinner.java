package br.com.gabrielrebechi.raffle.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "raffle_winner")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RaffleWinner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "draw_id", nullable = false)
    private RaffleDraw draw;

    @ManyToOne
    @JoinColumn(name = "participant_id", nullable = false)
    private Participant participant;

    @Column(name = "won_at", nullable = false, updatable = false)
    private LocalDateTime wonAt;

    @PrePersist
    protected void onWin() {
        this.wonAt = LocalDateTime.now();
    }
}