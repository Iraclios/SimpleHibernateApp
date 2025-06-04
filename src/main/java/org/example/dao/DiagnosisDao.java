package org.example.dao;

import org.example.entity.Diagnosis;
import org.hibernate.Session;

public class DiagnosisDao extends AbstractDao<Diagnosis> {
    public DiagnosisDao(Session session) {
        super(Diagnosis.class, session);
    }
}
