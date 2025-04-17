package ru.udsu.diplomserver.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Внешний идентификатор уязвимости (CVE, BID, другие системы нумерации).
 *
 * <p>Основные атрибуты:
 * <ul>
 *   <li>Тип идентификатора (CVE, BID и т.д.)</li>
 *   <li>Значение идентификатора</li>
 *   <li>Ссылка на официальное описание</li>
 * </ul>
 *
 * <p>Связан с:
 * <ul>
 *   <li>{@link Vulnerability} - уязвимости, имеющие этот идентификатор (отношение Many-to-Many)</li>
 * </ul>
 */
@Entity
@Table(name = "identifier")
@Getter
@Setter
public class Identifier {
    /**
     * Уникальный идентификатор в локальной базе
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Тип идентификатора
     * <p>Примеры: "CVE", "BID", "RHSA", "MSKB"
     */
    @Column(name = "type", nullable = false, length = 50)
    private String type;

    /**
     * Значение идентификатора
     * <p>Пример: "CVE-2021-44228"
     */
    @Column(name = "value", nullable = false, length = 100)
    private String value;

    /**
     * Ссылка на официальное описание
     * <p>Может быть URL или другим способом ссылки
     */
    @Column(name = "link", length = 500)
    private String link;

    @ManyToMany(mappedBy = "identifiers")
    private Set<Vulnerability> vulnerabilities = new HashSet<>();
}