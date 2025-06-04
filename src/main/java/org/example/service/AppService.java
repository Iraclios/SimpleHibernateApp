package org.example.service;

import org.example.dao.*;
import org.example.entity.Diagnosis;
import org.example.entity.Doctor;
import org.example.entity.Patient;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public final class AppService {
    private static final Session session = HibernateUtil.getSessionFactory().openSession();
    private static final DiagnosisDao diagnosisDao = new DiagnosisDao(session);
    private static final DoctorDao doctorDao = new DoctorDao(session);
    private static final PatientDao patientDao = new PatientDao(session);
    private static Scanner in;

    public static void enterLoop() {
        in = new Scanner(System.in);
        String operation, entity;
        try {
            operation = chooseOperation();
            while (!Objects.equals(operation, "Exit")) {
                entity = chooseEntity();
                if (!Objects.equals(entity, "Exit")) {
                    performOperationWithEntity(operation, entity);
                }
                operation = chooseOperation();
            }
        } finally {
            in.close();
        }
    }

    private static String chooseOperation() {
        System.out.println("Create - создать новую запись. " +
                "Read - прочесть записи. " +
                "Update - изменить запись. " +
                "Delete - удалить запись. " +
                "Exit - выйти.");
        String k = in.nextLine();
        return switch (k) {
            case "Create", "Read", "Update", "Delete" -> k;
            default -> "Exit";
        };

    }

    private static String chooseEntity() {
        System.out.println("Выберите сущность для взаимодействия: " +
                "Doctor - врач, " +
                "Patient - пациент, " +
                "Diagnosis - болезнь. " +
                "Exit - возврат назад.");
        String k = in.nextLine();
        return switch (k) {
            case "Doctor", "Patient", "Diagnosis" -> k;
            default -> "Exit";
        };
    }

    private static void performOperationWithEntity(String operation, String entity) {
        System.out.println();
        switch (operation + entity) {
            case "CreateDoctor":
                createDoctor();
                break;
            case "ReadDoctor":
                readDoctor();
                break;
            case "UpdateDoctor":
                updateDoctor();
                break;
            case "DeleteDoctor":
                deleteDoctor();
                break;

            case "CreatePatient":
                createPatient();
                break;
            case "ReadPatient":
                readPatient();
                break;
            case "UpdatePatient":
                updatePatient();
                break;
            case "DeletePatient":
                deletePatient();
                break;

            case "CreateDiagnosis":
                createDiagnosis();
                break;
            case "ReadDiagnosis":
                readDiagnosis();
                break;
            case "UpdateDiagnosis":
                updateDiagnosis();
                break;
            case "DeleteDiagnosis":
                deleteDiagnosis();
                break;
        }
    }

    private static void createDoctor() {
        Doctor doctor = new Doctor();
        System.out.println("Введите фамилию врача: ");
        doctor.setLastName(in.nextLine());
        System.out.println("Введите имя врача: ");
        doctor.setFirstName(in.nextLine());
        System.out.println("Введите отчество врача: ");
        doctor.setMiddleName(in.nextLine());
        System.out.println("Введите возраст врача: ");
        doctor.setAge(in.nextInt());
        in.nextLine();
        doctorDao.create(doctor);
    }

    private static void readDoctor() {
        List<Doctor> doctors = doctorDao.findAll();
        for (Doctor doctor : doctors) {
            System.out.printf("ID врача: %d%n", doctor.getId());
            System.out.printf("Фамилия врача: %s%n", doctor.getLastName());
            System.out.printf("Имя врача: %s%n", doctor.getFirstName());
            System.out.printf("Отчество врача: %s%n", doctor.getMiddleName());
            System.out.printf("Возраст врача: %d%n", doctor.getAge());
            System.out.println();
        }
    }

    private static void updateDoctor() {
        System.out.print("Введите id врача: ");
        Long id = in.nextLong();
        in.nextLine();
        Doctor doctor = doctorDao.findById(id);
        try {
            doctor.getAge();
        } catch (NullPointerException e) {
            System.out.printf("Нет объекта в базе данных с id = %d%n", id);
            return;
        }
        String k;
        do {
            System.out.println("Change LastName - изменить фамилию врача. Change Name - изменить имя врача. Change MiddleName - изменить отчество врача. Change Age - изменить возраст врача. Exit - выйти.");
            k = in.nextLine();
            switch (k) {
                case "Change LastName":
                    System.out.print("Введите новую фамилию врача: ");
                    doctor.setLastName(in.nextLine());
                    break;
                case "Change Name":
                    System.out.print("Введите новое имя врача: ");
                    doctor.setFirstName(in.nextLine());
                    break;
                case "Change MiddleName":
                    System.out.print("Введите новое отчество врача: ");
                    doctor.setMiddleName(in.nextLine());
                    break;
                case "Change Age":
                    System.out.print("Введите новый возраст врача: ");
                    doctor.setAge(in.nextInt());
                    in.nextLine();
                    break;
                case "Exit":
                    break;
                default:
                    break;
            }
        } while (!k.equals("Exit"));
        doctorDao.update(doctor);
    }

    private static void deleteDoctor() {
        System.out.print("Введите id врача: ");
        Long id = in.nextLong();
        in.nextLine();
        Doctor doctor = doctorDao.findById(id);
        try {
            doctor.getAge();
        } catch (NullPointerException e) {
            System.out.printf("Нет объекта в базе данных с id = %d%n", id);
            return;
        }
        doctorDao.delete(doctor);
    }

    private static void createPatient() {
        Patient patient = new Patient();
        System.out.print("Введите фамилию пациента: ");
        patient.setLastName(in.nextLine());
        System.out.print("Введите имя пациента: ");
        patient.setFirstName(in.nextLine());
        System.out.print("Введите отчество пациента: ");
        patient.setMiddleName(in.nextLine());
        System.out.print("Введите возраст пациента: ");
        patient.setAge(in.nextInt());
        in.nextLine();
        System.out.print("Введите id лечащего врача: ");
        Long id = in.nextLong();
        in.nextLine();
        try {
            Doctor doctor = doctorDao.findById(id);
            doctor.getAge();
            patient.setDoctor(doctor);
        } catch (NullPointerException e) {
            System.out.printf("Нет объекта в базе данных с id = %d%n", id);
            return;
        }
        String k;
        do {
            System.out.println("Add Diagnosis - добавить этому пациенту диагноз. Exit - выйти.");
            k = in.nextLine();
            switch (k) {
                case "Add Diagnosis": {
                    System.out.print("Введите id диагноза: ");
                    Long id1 = in.nextLong();
                    in.nextLine();
                    Diagnosis diagnosis = diagnosisDao.findById(id1);
                    try {
                        diagnosis.getName();
                    } catch (NullPointerException e) {
                        System.out.printf("Нет объекта в базе данных с id = %d%n", id1);
                        return;
                    }
                    patient.getDiagnoses().add(diagnosis);
                }
                break;
                case "Exit":
                    break;
                default:
                    break;
            }
        } while (!k.equals("Exit"));
        patientDao.create(patient);
    }

    private static void readPatient() {
        List<Patient> patients = patientDao.findAll();
        for (Patient patient : patients) {
            System.out.printf("ID пациента: %d%n", patient.getId());
            System.out.printf("Фамилия пациента: %s%n", patient.getLastName());
            System.out.printf("Имя пациента: %s%n", patient.getFirstName());
            System.out.printf("Отчество пациента: %s%n", patient.getMiddleName());
            System.out.printf("Возраст пациента: %s%n", patient.getAge());
            System.out.printf("ID лечащего врача пациента: %d%n", patient.getDoctor().getId());
            boolean t = true;
            for (Diagnosis diagnosis : patient.getDiagnoses()) {
                if (t) {
                    t = false;
                    System.out.printf("Имеет диагнозы: %d", diagnosis.getId());
                } else {
                    System.out.printf(", %d", diagnosis.getId());
                }
            }
            if (!t) System.out.println();
            System.out.println();
        }
    }

    private static void updatePatient() {
        System.out.print("Введите id пациента: ");
        Long id = in.nextLong();
        in.nextLine();
        Patient patient = patientDao.findById(id);
        try {
            patient.getAge();
        } catch (NullPointerException e) {
            System.out.printf("Нет объекта в базе данных с id = %d%n", id);
            return;
        }
        String k;
        do {
            System.out.println("Change LastName - изменить фамилию пациента. Change Name - изменить имя пациента. Change MiddleName - изменить отчество пациента. Change Age - изменить возраст пациента. Add Diagnosis - добавить этому пациенту диагноз. Remove Diagnosis - убрать этому пациенту диагноз. Exit - выйти.");
            k = in.nextLine();
            switch (k) {
                case "Change LastName":
                    System.out.print("Введите новую фамилию пациента: ");
                    patient.setLastName(in.nextLine());
                    break;
                case "Change Name":
                    System.out.print("Введите новое имя пациента: ");
                    patient.setFirstName(in.nextLine());
                    break;
                case "Change MiddleName":
                    System.out.print("Введите новое отчество пациента: ");
                    patient.setMiddleName(in.nextLine());
                    break;
                case "Change Age":
                    System.out.print("Введите новый возраст пациента: ");
                    patient.setAge(in.nextInt());
                    in.nextLine();
                    break;
                case "Add Diagnosis": {
                    System.out.print("Введите id диагноза: ");
                    Long id1 = in.nextLong();
                    in.nextLine();
                    Diagnosis diagnosis = diagnosisDao.findById(id1);
                    try {
                        diagnosis.getName();
                    } catch (NullPointerException e) {
                        System.out.printf("Нет объекта в базе данных с id = %d%n", id1);
                        return;
                    }
                    patient.getDiagnoses().add(diagnosis);
                }
                break;
                case "Remove Diagnosis": {
                    System.out.print("Введите id диагноза: ");
                    Long id1 = in.nextLong();
                    in.nextLine();
                    Diagnosis diagnosis = diagnosisDao.findById(id1);
                    try {
                        diagnosis.getName();
                    } catch (NullPointerException e) {
                        System.out.printf("Нет объекта в базе данных с id = %d%n", id1);
                        return;
                    }
                    patient.getDiagnoses().remove(diagnosis);
                }
                break;
                case "Change Doctor":
                    System.out.print("Введите id нового врача пациента: ");
                    Long id1 = in.nextLong();
                    in.nextLine();
                    Doctor doctor = doctorDao.findById(id1);
                    try {
                        doctor.getAge();
                    } catch (NullPointerException e) {
                        System.out.printf("Нет объекта в базе данных с id = %d%n", id1);
                        return;
                    }
                    patient.setDoctor(doctor);
                    break;
                case "Exit":
                    break;
                default:
                    break;
            }
        } while (!k.equals("Exit"));
        patientDao.update(patient);
    }

    private static void deletePatient() {

        System.out.print("Введите id пациента: ");
        Long id = in.nextLong();
        in.nextLine();
        Patient patient = patientDao.findById(id);
        try {
            patient.getAge();
        } catch (NullPointerException e) {
            System.out.printf("Нет объекта в базе данных с id = %d%n", id);
            return;
        }
        for (Diagnosis diagnosis : patient.getDiagnoses()) {
            diagnosis.getPatients().remove(patient);
        }
        patient.getDiagnoses().clear();
        patientDao.delete(patient);
    }

    private static void createDiagnosis() {

        Diagnosis diagnosis = new Diagnosis();
        System.out.print("Введите имя диагноза: ");
        diagnosis.setName(in.nextLine());
        String k;
        do {
            System.out.println("Add Patient - добавить пациенту этот диагноз. Exit - выйти.");
            k = in.nextLine();
            switch (k) {
                case "Add Patient": {
                    System.out.print("Введите id пациента: ");
                    Long id1 = in.nextLong();
                    in.nextLine();
                    Patient patient = patientDao.findById(id1);
                    try {
                        patient.getAge();
                    } catch (NullPointerException e) {
                        System.out.printf("Нет объекта в базе данных с id = %d%n", id1);
                        return;
                    }
                    diagnosis.getPatients().add(patient);
                }
                break;
                case "Exit":
                    break;
                default:
                    break;
            }
        } while (!k.equals("Exit"));
        diagnosisDao.create(diagnosis);
    }

    private static void readDiagnosis() {
        List<Diagnosis> diagnoses = diagnosisDao.findAll();
        for (Diagnosis diagnosis : diagnoses) {
            System.out.printf("ID диагноза: %d%n", diagnosis.getId());
            System.out.printf("Имя диагноза: %s%n", diagnosis.getName());
            boolean t = true;
            for (Patient patient : diagnosis.getPatients()) {
                if (t) {
                    t = false;
                    System.out.printf("Имеющие диагноз: %d", patient.getId());
                } else {
                    System.out.printf(", %d", patient.getId());
                }
            }
            if (!t) System.out.println();
            System.out.println();
        }
    }

    private static void updateDiagnosis() {

        System.out.print("Введите id диагноза: ");
        Long id = in.nextLong();
        in.nextLine();
        Diagnosis diagnosis = diagnosisDao.findById(id);
        try {
            diagnosis.getName();
        } catch (NullPointerException e) {
            System.out.printf("Нет объекта в базе данных с id = %d%n", id);
            return;
        }
        String k;
        do {
            System.out.println("Change Name - изменить название диагноза. Add Patient - добавить пациенту этот диагноз. Remove Patient - убрать пациенту этот диагноз. Exit - выйти.");
            k = in.nextLine();
            switch (k) {
                case "Change Name":
                    System.out.print("Введите новое имя диагноза: ");
                    diagnosis.setName(in.nextLine());
                    break;
                case "Add Patient": {
                    System.out.print("Введите id пациента: ");
                    Long id1 = in.nextLong();
                    in.nextLine();
                    Patient patient = patientDao.findById(id1);
                    try {
                        patient.getAge();
                    } catch (NullPointerException e) {
                        System.out.printf("Нет объекта в базе данных с id = %d%n", id1);
                        return;
                    }
                    patient.getDiagnoses().add(diagnosis);
                    diagnosis.getPatients().add(patient);
                }
                break;
                case "Remove Patient": {
                    System.out.print("Введите id пациента: ");
                    Long id1 = in.nextLong();
                    in.nextLine();
                    Patient patient = patientDao.findById(id1);
                    try {
                        patient.getAge();
                    } catch (NullPointerException e) {
                        System.out.printf("Нет объекта в базе данных с id = %d%n", id1);
                        return;
                    }
                    patient.getDiagnoses().remove(diagnosis);
                    diagnosis.getPatients().remove(patient);
                }
                break;
                case "Exit":
                    break;
                default:
                    break;
            }
        } while (!k.equals("Exit"));
        diagnosisDao.update(diagnosis);
    }

    private static void deleteDiagnosis() {
        System.out.print("Введите id диагноза: ");
        Long id = in.nextLong();
        in.nextLine();
        Diagnosis diagnosis = diagnosisDao.findById(id);
        try {
            diagnosis.getName();
        } catch (NullPointerException e) {
            System.out.printf("Нет объекта в базе данных с id = %d%n", id);
            return;
        }
        for (Patient patient : diagnosis.getPatients()) {
            patient.getDiagnoses().remove(diagnosis);
        }
        diagnosis.getPatients().clear();
        diagnosisDao.delete(diagnosis);
    }
}
