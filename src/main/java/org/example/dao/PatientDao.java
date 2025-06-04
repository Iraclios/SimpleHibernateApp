package org.example.dao;

import org.example.entity.Patient;
import org.hibernate.Session;

public class PatientDao extends AbstractDao<Patient> {
    public PatientDao(Session session) {
        super(Patient.class, session);
    }
}
