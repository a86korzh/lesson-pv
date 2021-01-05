package com.korzh86a.lessons_pv.dao;

import com.korzh86a.lessons_pv.entity.Mark;
import com.korzh86a.lessons_pv.entity.Student;
import com.korzh86a.lessons_pv.entity.Subject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SchoolDao implements AutoCloseable{
    private SessionFactory sessionFactory;

    public SchoolDao() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Student.class)
                .addAnnotatedClass(Subject.class)
                .addAnnotatedClass(Mark.class)
                .configure("hibernate.cfg.xml");

        ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        sessionFactory = configuration.buildSessionFactory(serviceRegistryObj);
    }

    public void add(Object schoolObject) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(schoolObject);
        transaction.commit();
        if (session!=null) {
            session.close();
        }
    }

    public void update(Object schoolObject) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(schoolObject);
        transaction.commit();
        if (session!=null) {
            session.close();
        }
    }

    public List<Student> getAllStudents() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Student> students = session.createQuery("FROM Student").list();
        transaction.commit();
        if (session!=null) {
            session.close();
        }
        return students;
    }

    public Student getStudent(int studentId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Student student = session.get(Student.class, studentId);
        transaction.commit();
        if (session!=null) {
            session.close();
        }
        return student;
    }

    public Map<Subject, List<Mark>> getStudentMarks(Student student) {
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
    }

    public void removeStudent(Student student) {
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
    }

    public List<Subject> getAllSubjects() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Subject> subjects = session.createQuery("FROM Subject").list();
        transaction.commit();
        if (session!=null) {
            session.close();
        }
        return subjects;
    }

    public Subject getSubject(int studentId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Subject subject = session.get(Subject.class, studentId);
        transaction.commit();
        if (session!=null) {
            session.close();
        }
        return subject;
    }

    public void removeSubjectById(int subjectId) {
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
    }

    public Mark getMark(int markId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Mark mark = session.get(Mark.class, markId);
        transaction.commit();
        if (session!=null) {
            session.close();
        }
        return mark;
    }

    public void removeMarkById(int markId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Mark mark = session.get(Mark.class, markId);
        session.delete(mark);
        transaction.commit();
        if (session!=null) {
            session.close();
        }
    }

    @Override
    public void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
