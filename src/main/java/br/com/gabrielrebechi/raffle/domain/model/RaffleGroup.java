package br.com.gabrielrebechi.raffle.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "raffle_group", uniqueConstraints = @UniqueConstraint(columnNames = "keyword"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RaffleGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "keyword", nullable = false, unique = true)
    private String keyword;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Participant> participants;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
