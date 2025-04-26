package ru.udsu.diplomserver.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sop")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Sop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private SlOperProcs slOperProcs;
}
