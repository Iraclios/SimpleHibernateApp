package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "doctors")
@Getter
public class Doctor {
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

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Patient> patients;

    @Override
    public String toString() {
        return "ID " + id + ": " + lastName + " " + firstName + " " + middleName + ", " + age + " лет";
    }
}
