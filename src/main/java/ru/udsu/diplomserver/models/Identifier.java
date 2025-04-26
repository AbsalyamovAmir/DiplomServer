package ru.udsu.diplomserver.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "identifiers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Identifier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "value")
    private String value;

    @Column(name = "link", columnDefinition = "TEXT")
    private String link;

    @ManyToOne
    private Vulnerability vulnerability;
}
