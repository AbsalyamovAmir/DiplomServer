package ru.udsu.diplomserver.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Производитель программного обеспечения или операционных систем.
 * Содержит базовую информацию о компании-разработчике.
 *
 * <p>Связан с:
 * <ul>
 *   <li>{@link Software} - продукты этого производителя (отношение One-to-Many)</li>
 *   <li>{@link OperatingSystem} - ОС этого производителя (отношение One-to-Many)</li>
 * </ul>
 */
@Entity
@Table(name = "vendor")
@Getter
@Setter
public class Vendor {

    /**
     * Уникальный идентификатор производителя в системе
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название компании-производителя
     * <p>Обязательное поле, максимальная длина 255 символов
     */
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "vendor")
    private Set<Software> softwareProducts = new HashSet<>();

    @OneToMany(mappedBy = "vendor")
    private Set<OperatingSystem> operatingSystems = new HashSet<>();
}
