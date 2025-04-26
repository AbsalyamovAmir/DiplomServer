package ru.udsu.diplomserver.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "software_types")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SoftwareType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false)
    private String type;

    @ManyToOne
    private VulnerableSoftware software;
}
