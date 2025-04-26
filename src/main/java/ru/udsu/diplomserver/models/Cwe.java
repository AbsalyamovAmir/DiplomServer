package ru.udsu.diplomserver.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cwe")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Cwe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "identifier", nullable = false)
    private String identifier;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private Cwes cwes;
}
