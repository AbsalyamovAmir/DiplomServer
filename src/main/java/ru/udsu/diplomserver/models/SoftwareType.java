package ru.udsu.diplomserver.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Классификатор типов программного обеспечения.
 * Позволяет категоризировать ПО по функциональному назначению.
 *
 * <p>Примеры типов: "Антивирус", "Офисное ПО", "СУБД", "Графический редактор"
 *
 * <p>Связан с:
 * <ul>
 *   <li>{@link Software} - продукты этого типа (отношение Many-to-Many через таблицу software_types)</li>
 * </ul>
 */
@Entity
@Table(name = "software_type")
@Getter
@Setter
public class SoftwareType {

    /**
     * Уникальный идентификатор типа ПО
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название типа программного обеспечения
     * <p>Обязательное поле, максимальная длина 100 символов
     */
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @ManyToMany(mappedBy = "types")
    private Set<Software> softwareProducts = new HashSet<>();
}
