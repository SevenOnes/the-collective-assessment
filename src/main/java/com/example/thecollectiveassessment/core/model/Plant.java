package com.example.thecollectiveassessment.core.model;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "Plant")
@Table(name = "plant", indexes = {@Index(columnList = "location"), @Index(columnList = "is_active"), @Index(columnList = "generator_id")})
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Accessors(chain = true)
public class Plant implements Serializable {

    @Id
    @SequenceGenerator(
            name = "plant_sequence_id",
            sequenceName = "plant_sequence_id",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "plant_sequence_id"
    )
    @Column(name = "id", updatable = false)
    private Integer id;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    private String name;

    @Column(name = "generator_id", nullable = false)
    private String generatorId;

    @Column(name = "generator_status", nullable = false, columnDefinition = "TEXT")
    private String generatorStatus;

    @Column(name = "generation_amount")
    private Long generationAmount;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    public Plant(
            Integer id,
            Integer year,
            String location,
            String name,
            String generatorId,
            String generatorStatus,
            Long generationAmount,
            Boolean isActive) {
        this.id = id;
        this.location = location;
        this.year = year;
        this.name = name;
        this.generatorId = generatorId;
        this.generatorStatus = generatorStatus;
        this.generationAmount = generationAmount;
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Plant plant = (Plant) o;
        return id != null && Objects.equals(id, plant.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
