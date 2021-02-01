package com.korzh86a.lessons.pv.dao;

import com.korzh86a.lessons.pv.entity.Mark;
import com.korzh86a.lessons.pv.entity.Student;
import com.korzh86a.lessons.pv.entity.Subject;
import com.korzh86a.lessons.pv.entity.SubjectWithMarks;
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
                    .addAnnotatedClass(SubjectWithMarks.class)
                    .configure("hibernate.cfg.xml");

            ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistryObj);
        } catch (Exception e) {
            throw new SchoolDaoException("Exception while create connection", e);
        }
    }

        public long getSubjectAmount () {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            String queryStr = "SELECT COUNT(e) FROM Subject e";

            long result = (long)session.createQuery(queryStr).getSingleResult();

            transaction.commit();
            if (session!=null) {
                session.close();
            }

            return result;
        }

        public long getStudentAmount () {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            String queryStr = "SELECT COUNT(e) FROM Student e";

            long result = (long)session.createQuery(queryStr).getSingleResult();

            transaction.commit();
            if (session!=null) {
                session.close();
            }

            return result;
        }

    public List<Student> getAllStudents(int representedStudentsAmount, int actualPageFirstRow)
            throws SchoolDaoException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Query<Student> query = session.createQuery("FROM Student order by secondName");
            query.setMaxResults(representedStudentsAmount);
            query.setFirstResult(actualPageFirstRow);

            List<Student> result = query.list();

            transaction.commit();
            if (session!=null) {
                session.close();
            }

            return result;

        } catch (Exception e) {
            throw new SchoolDaoException("Exception while get all student", e);
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

    public Map<Subject, List<Mark>> getStudentMarks(Student student) throws SchoolDaoException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            Student temp = session.get(Student.class, student.getId());

            transaction.commit();
            if (session!=null) {
                session.close();
            }

            Map<Subject, List<Mark>> result = new HashMap<>();
            for (SubjectWithMarks s : temp.getSubjectsWithMarks()) {
                result.put(s.getSubject(), s.getMarks());
            }

            return result;

        } catch (Exception e) {
            throw new SchoolDaoException("Exception while get marks", e);
        }
    }

    public void addSchoolObject(Object schoolObject) throws SchoolDaoException {
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

    public void updateSchoolDao(Object schoolObject) throws SchoolDaoException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.update(schoolObject);

            transaction.commit();
            if (session!=null) {
                session.close();
            }
        } catch (Exception e) {
            throw new SchoolDaoException("Exception while update entity", e);
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

    public void removeStudent(int studentId) throws SchoolDaoException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Query theQuery =
                    session.createQuery("delete from Student where id=:studentId");
            theQuery.setParameter("studentId", studentId);

            theQuery.executeUpdate();

            transaction.commit();
            if (session!=null) {
                session.close();
            }
        } catch (Exception e) {
            throw new SchoolDaoException("Exception while remove student", e);
        }
    }

    public void removeSubject(int subjectId) throws SchoolDaoException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Query theQuery =
                    session.createQuery("delete from Subject where id=:subjectId");
            theQuery.setParameter("subjectId", subjectId);

            theQuery.executeUpdate();

            transaction.commit();
            if (session!=null) {
                session.close();
            }
        } catch (Exception e) {
            throw new SchoolDaoException("Exception while remove subject", e);
        }
    }

    @Override
    public void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
