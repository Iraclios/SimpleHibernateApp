package org.example.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "diagnoses")
@Getter
public class Diagnosis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Не указано название")
    @Column(nullable = false)
    @Setter
    private String name;

    @ManyToMany(mappedBy = "diagnoses")
    private Set<Patient> patients = new HashSet<>();

    @Override
    public String toString() {
        return "ID " + id + ": " + name;
    }
}