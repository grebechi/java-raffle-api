package br.com.gabrielrebechi.raffle.model;

import br.com.gabrielrebechi.raffle.model.base.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(
        name = "raffle_group",
        uniqueConstraints = @UniqueConstraint(columnNames = {"keyword"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RaffleGroup extends Auditable {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "keyword",nullable = false)
    private String keyword;

    @Column(name = "description")
    private String description;
}
