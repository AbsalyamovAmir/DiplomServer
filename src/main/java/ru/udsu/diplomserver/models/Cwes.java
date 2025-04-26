package ru.udsu.diplomserver.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cwes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Cwes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cwes", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cwe> cweList = new ArrayList<>();

    @OneToOne
    private Vulnerability vulnerability;
}
