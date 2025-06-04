package org.example.dao;

import org.example.entity.Doctor;
import org.hibernate.Session;

public class DoctorDao extends AbstractDao<Doctor> {
    public DoctorDao(Session session) {
        super(Doctor.class, session);
    }
}
