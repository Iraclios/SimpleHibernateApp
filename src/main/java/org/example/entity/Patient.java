package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "patients")
@Getter
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Не указана фамилия")
    @Column(nullable = false)
    @Setter
    private String lastName;

    @NotNull(message = "Не указано имя")
    @Column(nullable = false)
    @Setter
    private String firstName;

    @Setter
    private String middleName;

    @NotNull(message = "Не указан возраст")
    @Column(nullable = false)
    @Min(value = 0, message = "Возраст не может быть меньше {value}")
    @Max(value = 111, message = "Возраст должен быть не больше {value}")
    @Setter
    private Integer age;

    @ManyToOne(optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    @NotNull(message = "Не указан лечащий врач")
    @Setter
    private Doctor doctor;

    @ManyToMany
    @JoinTable(
            name = "patient_diagnosis",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "diagnosis_id")
    )
    private Set<Diagnosis> diagnoses = new HashSet<>();

    @Override
    public String toString() {
        return "ID " + id + ": " + lastName + " " + firstName + " " + middleName + ", " + age
                + " лет. ID врача: " + doctor.getId();
    }
}