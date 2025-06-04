package org.example.dao;

import org.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public abstract class AbstractDao<T> {
    private final Class<T> type;
    private final Session session;

    public AbstractDao(final Class<T> type, Session session)   {
        this.type = type;
        this.session = session;
    }

    public void create(T entity) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public T findById(Long id) {
        return session.get(type, id);
    }

    public List<T> findAll() {
        return session.createQuery("from " + type.getName(), type).list();
    }

    public void update(T entity) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void delete(T entity) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
