package ru.udsu.diplomserver.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Источник информации об уязвимости (база данных, веб-сайт и т.д.).
 *
 * <p>Основные атрибуты:
 * <ul>
 *   <li>URL источника</li>
 *   <li>Описание источника</li>
 * </ul>
 *
 * <p>Связан с:
 * <ul>
 *   <li>{@link Vulnerability} - уязвимости, упомянутые в этом источнике (отношение Many-to-Many)</li>
 * </ul>
 */
@Entity
@Table(name = "source")
@Getter
@Setter
public class Source {
    /**
     * Уникальный идентификатор источника
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * URL источника информации
     * <p>Пример: "https://nvd.nist.gov/vuln/detail/CVE-2021-44228"
     */
    @Column(name = "url", nullable = false, length = 500)
    private String url;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "source_id")
    private Set<Vulnerability> vulnerabilities = new HashSet<>();
}
