package com.korzh86a.lessons.pv.dao;

import com.korzh86a.lessons.pv.entity.Mark;
import com.korzh86a.lessons.pv.entity.Student;
import com.korzh86a.lessons.pv.entity.Subject;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SchoolDao implements AutoCloseable{
    private SessionFactory sessionFactory;

    public SchoolDao() throws SchoolDaoException {
        try {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(Student.class)
                    .addAnnotatedClass(Subject.class)
                    .addAnnotatedClass(Mark.class)
                    .configure("hibernate.cfg.xml");

            ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistryObj);
        } catch (Exception e) {
            throw new SchoolDaoException("Exception while create connection", e);
        }
    }

    public void add(Object schoolObject) throws SchoolDaoException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.save(schoolObject);
            transaction.commit();
            if (session!=null) {
                session.close();
            }
        } catch (Exception e) {
            throw new SchoolDaoException("Exception while add entity", e);
        }
    }

    public void update(Object schoolObject) throws SchoolDaoException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.update(schoolObject);
            transaction.commit();
            if (session!=null) {
                session.close();
            }
        } catch (HibernateException e) {
        } catch (Exception e) {
            throw new SchoolDaoException("Exception while update entity", e);
        }
    }

    public List<Student> getAllStudents() throws SchoolDaoException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            List<Student> students = session.createQuery("FROM Student").list();
            transaction.commit();
            if (session!=null) {
                session.close();
            }
            return students;
        } catch (Exception e) {
            throw new SchoolDaoException("Exception while get all student", e);
        }
    }

    public Student getStudent(int studentId) throws SchoolDaoException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            Student student = session.get(Student.class, studentId);
            transaction.commit();
            if (session!=null) {
                session.close();
            }
            return student;
        } catch (Exception e) {
            throw new SchoolDaoException("Exception while get student", e);
        }
    }

    public Map<Subject, List<Mark>> getStudentMarks(Student student) throws SchoolDaoException {
        try {
            Map<Subject, List<Mark>> map = new HashMap<>();

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery("FROM Mark WHERE studentId = :studentId");
            query.setParameter("studentId", student.getId());
            List<Mark> marks = query.list();

            Subject subject;
            List<Mark> temp;

            for (Mark m : marks) {
                subject = session.get(Subject.class, m.getSubjectId());

                if (map.containsKey(subject)){
                    temp = map.get(subject);
                } else {
                    temp = new ArrayList<>();
                }

                temp.add(m);
                map.put(subject, temp);
            }

            transaction.commit();
            if (session!=null) {
                session.close();
            }
            return map;
        } catch (Exception e) {
            throw new SchoolDaoException("Exception while get marks", e);
        }
    }

    public void removeStudent(Student student) throws SchoolDaoException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery("DELETE FROM Mark WHERE studentId = :studentId");
            query.setParameter("studentId", student.getId());
            query.executeUpdate();

            session.delete(student);
            transaction.commit();
            if (session!=null) {
                session.close();
            }
        } catch (Exception e) {
            throw new SchoolDaoException("Exception while remove student", e);
        }
    }

    public List<Subject> getAllSubjects() throws SchoolDaoException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            List<Subject> subjects = session.createQuery("FROM Subject").list();
            transaction.commit();
            if (session!=null) {
                session.close();
            }
            return subjects;
        } catch (Exception e) {
            throw new SchoolDaoException("Exception while get all subject", e);
        }
    }

    public Subject getSubject(int studentId) throws SchoolDaoException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            Subject subject = session.get(Subject.class, studentId);
            transaction.commit();
            if (session!=null) {
                session.close();
            }
            return subject;
        } catch (Exception e) {
            throw new SchoolDaoException("Exception while get subject", e);
        }
    }

    public void removeSubjectById(int subjectId) throws SchoolDaoException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery("DELETE FROM Mark WHERE subjectId = :subjectId");
            query.setParameter("subjectId", subjectId);
            query.executeUpdate();

            Subject subject = session.get(Subject.class, subjectId);
            session.delete(subject);
            transaction.commit();
            if (session!=null) {
                session.close();
            }
        } catch (Exception e) {
            throw new SchoolDaoException("Exception while remove subject", e);
        }
    }

    public Mark getMark(int markId) throws SchoolDaoException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            Mark mark = session.get(Mark.class, markId);
            transaction.commit();
            if (session!=null) {
                session.close();
            }
            return mark;
        } catch (Exception e) {
            throw new SchoolDaoException("Exception while get mark", e);
        }
    }

    public void removeMarkById(int markId) throws SchoolDaoException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            Mark mark = session.get(Mark.class, markId);
            session.delete(mark);
            transaction.commit();
            if (session!=null) {
                session.close();
            }
        } catch (Exception e) {
            throw new SchoolDaoException("Exception while remove mark", e);
        }
    }

    @Override
    public void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
