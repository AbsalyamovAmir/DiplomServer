package ru.udsu.diplomserver.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cvss")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Cvss {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vector")
    private String vector;

    @Column(name = "score")
    private Double score;

    @OneToOne
    private Vulnerability vulnerability;
}
