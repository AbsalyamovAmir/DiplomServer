package ru.udsu.diplomserver.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Программный продукт. Содержит информацию о конкретной версии ПО.
 *
 * <p>Основные атрибуты:
 * <ul>
 *   <li>Название и версия продукта</li>
 *   <li>Поддерживаемая платформа</li>
 *   <li>Связь с производителем</li>
 *   <li>Классификация по типам</li>
 * </ul>
 *
 * <p>Связан с:
 * <ul>
 *   <li>{@link Vendor} - производитель (отношение Many-to-One)</li>
 *   <li>{@link SoftwareType} - типы продукта (отношение Many-to-Many)</li>
 *   <li>{@link Vulnerability} - уязвимости этого ПО (отношение Many-to-Many)</li>
 * </ul>
 */
@Entity
@Table(name = "software")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Software {
    /**
     * Уникальный идентификатор программного продукта
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Производитель данного программного продукта
     * <p>Ссылка на {@link Vendor}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    /**
     * Наименование программного продукта
     * <p>Обязательное поле, максимальная длина 255 символов
     */
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    /**
     * Версия программного продукта
     * <p>Формат версии зависит от политики производителя
     */
    @Column(name = "version", length = 100)
    private String version;

    /**
     * Поддерживаемая платформа/архитектура
     * <p>Примеры: "Windows", "Linux", "macOS", "x86", "ARM"
     */
    @Column(name = "platform", length = 100)
    private String platform;

    /**
     * Типы, к которым относится данное программное обеспечение
     * <p>Связь Many-to-Many через таблицу software_types
     */
    @ManyToMany
    @JoinTable(
            name = "software_types",
            joinColumns = @JoinColumn(name = "software_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    private Set<SoftwareType> types;

    @ManyToMany(mappedBy = "affectedSoftware")
    private Vulnerability vulnerabilitie;
}