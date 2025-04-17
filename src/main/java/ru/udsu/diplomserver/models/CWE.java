package ru.udsu.diplomserver.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Запись из базы Common Weakness Enumeration (CWE).
 * Описывает класс уязвимостей, а не конкретные экземпляры.
 *
 * <p>Основные атрибуты:
 * <ul>
 *   <li>CWE-ID (идентификатор в формате CWE-XXX)</li>
 *   <li>Название класса уязвимостей</li>
 *   <li>Подробное описание</li>
 * </ul>
 *
 * <p>Связан с:
 * <ul>
 *   <li>{@link Vulnerability} - конкретные уязвимости этого класса (отношение Many-to-Many)</li>
 * </ul>
 */
@Entity
@Table(name = "cwe")
@Getter
@Setter
public class CWE {
    /**
     * Уникальный идентификатор записи в локальной базе
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Идентификатор CWE в формате CWE-XXX
     * <p>Пример: "CWE-79" - XSS (Cross-site Scripting)
     */
    @Column(name = "identifier", nullable = false, length = 20)
    private String identifier;

    /**
     * Название класса уязвимости
     */
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    /**
     * Подробное описание класса уязвимости
     * <p>Может содержать примеры, условия возникновения и другую информацию
     */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToMany(mappedBy = "cweEntries")
    private Set<Vulnerability> vulnerabilities = new HashSet<>();
}
