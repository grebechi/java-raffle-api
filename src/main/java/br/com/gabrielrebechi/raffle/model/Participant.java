package br.com.gabrielrebechi.raffle.model;

import br.com.gabrielrebechi.raffle.model.base.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(
        name = "participant",
        uniqueConstraints = @UniqueConstraint(columnNames = {"email"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Participant extends Auditable {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false)
    private String email;

}
