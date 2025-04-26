package ru.udsu.diplomserver.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vulnerable_software")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class VulnerableSoftware {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vendor")
    private String vendor;

    @Column(name = "name", columnDefinition = "TEXT")
    private String name;

    @Column(name = "version")
    private String version;

    @Column(name = "platform")
    private String platform;

    @OneToMany(mappedBy = "software", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SoftwareType> types = new ArrayList<>();

    @ManyToOne
    private Vulnerability vulnerability;
}