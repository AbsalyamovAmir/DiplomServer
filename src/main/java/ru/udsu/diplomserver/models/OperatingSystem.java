package ru.udsu.diplomserver.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

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
@Getter
@Setter
public class OperatingSystem {
    /**
     * Уникальный идентификатор операционной системы
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Производитель данной операционной системы
     * <p>Ссылка на {@link Vendor}
     */
    @ManyToOne(cascade = CascadeType.ALL)
    private Vendor vendor;

    /**
     * Наименование операционной системы
     * <p>Обязательное поле, максимальная длина 255 символов
     */
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    /**
     * Версия операционной системы
     * <p>Может включать номер сборки, service pack и т.д.
     */
    @Column(name = "version", length = 100)
    private String version;

    /**
     * Поддерживаемая платформа/архитектура
     * <p>Примеры: "x86", "x64", "ARM", "PowerPC"
     */
    @Column(name = "platform", length = 100)
    private String platform;

    @ManyToOne(cascade = CascadeType.ALL)
    private Vulnerability vulnerability;

}
