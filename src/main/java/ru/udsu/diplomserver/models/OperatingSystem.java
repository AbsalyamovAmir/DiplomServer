package ru.udsu.diplomserver.models;

import jakarta.persistence.*;
import lombok.*;

/**
 * Операционная система. Содержит информацию о конкретной версии ОС.
 *
 * <p>Основные атрибуты:
 * <ul>
 *   <li>Название и версия ОС</li>
 *   <li>Поддерживаемая платформа</li>
 *   <li>Связь с производителем</li>
 * </ul>
 *
 * <p>Связан с:
 * <ul>
 *   <li>{@link Vendor} - производитель ОС (отношение Many-to-One)</li>
 *   <li>{@link Vulnerability} - уязвимости этой ОС (отношение Many-to-Many)</li>
 * </ul>
 */
@Entity
@Table(name = "operating_system")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OperatingSystem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vendor")
    private String vendor;

    @Column(name = "name")
    private String name;

    @Column(name = "version")
    private String version;

    @Column(name = "platform")
    private String platform;

    @ManyToOne
    private Environment environment;
}
