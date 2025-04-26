package ru.udsu.diplomserver.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sources")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Source {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source", columnDefinition = "TEXT")
    private String source;

    @ManyToOne
    private Vulnerability vulnerability;
}
