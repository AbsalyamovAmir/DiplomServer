package ru.udsu.diplomserver.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sl_oper_procs")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SlOperProcs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "slOperProcs", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sop> sopList = new ArrayList<>();

    @OneToOne
    private Vulnerability vulnerability;
}
