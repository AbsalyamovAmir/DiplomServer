package ru.udsu.diplomserver.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "environment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Environment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "environment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OperatingSystem> osList = new ArrayList<>();

    @OneToOne
    private Vulnerability vulnerability;
}
