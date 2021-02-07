package com.korzh86a.lessons.pv.dao;

import com.korzh86a.lessons.pv.entity.Mark;
import com.korzh86a.lessons.pv.entity.Student;
import com.korzh86a.lessons.pv.entity.Subject;
import com.korzh86a.lessons.pv.entity.SubjectWithMarks;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SchoolDao implements AutoCloseable{
    private SessionFactory sessionFactory;
    private final String SUBJECT_AMOUNT = "SELECT COUNT(e) FROM Subject e";
    private final String STUDENT_AMOUNT = "SELECT COUNT(e) FROM Student e";
    private final String ALL_STUDENTS = "FROM Student order by secondName";
    private final String ALL_SUBJECT = "FROM Subject";
    private final String REMOVE_STUDENT = "delete from Student where id=:studentId";
    private final String REMOVE_SUBJECT = "delete from Subject where id=:subjectId";

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

        long result = (long)session.createQuery(SUBJECT_AMOUNT).getSingleResult();

        if (session!=null) {
            session.close();
        }

        return result;
    }

    public long getStudentAmount () {
        Session session = sessionFactory.openSession();

        long result = (long)session.createQuery(STUDENT_AMOUNT).getSingleResult();

        if (session!=null) {
            session.close();
        }

        return result;
    }

    public List<Student> getAllStudents(int representedStudentsAmount, int actualPageFirstRow)
            throws SchoolDaoException {
        try {
            Session session = sessionFactory.openSession();

            Query<Student> query = session.createQuery(ALL_STUDENTS);
            query.setMaxResults(representedStudentsAmount);
            query.setFirstResult(actualPageFirstRow);

            List<Student> result = query.list();

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

            List<Subject> subjects = session.createQuery(ALL_SUBJECT).list();

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
            Student temp = session.get(Student.class, student.getId());

            Map<Subject, List<Mark>> result = new HashMap<>();
            for (SubjectWithMarks s : temp.getSubjectsWithMarks()) {
                result.put(s.getSubject(), s.getMarks());
            }

            if (session!=null) {
                session.close();
            }

            return result;

        } catch (Exception e) {
            throw new SchoolDaoException("Exception while get marks", e);
        }
    }

    public void addSchoolDao (Object schoolObject) throws SchoolDaoException {
        try {
            Session session = sessionFactory.openSession();
            session.save(schoolObject);

            if (session!=null) {
                session.close();
            }

        } catch (Exception e) {
            throw new SchoolDaoException("Exception while add entity", e);
        }
    }

    public void updateStudent(Student studentForm) throws SchoolDaoException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Student student = session.get(Student.class, studentForm.getId());
            student.setEnterYear(studentForm.getEnterYear());
            student.setFirstName(studentForm.getFirstName());
            student.setSecondName(studentForm.getSecondName());
            student.setBirthDate(studentForm.getBirthDate());
            System.out.println(student);
            session.update(student);

            transaction.commit();

            if (session!=null) {
                session.close();
            }
        } catch (Exception e) {
            throw new SchoolDaoException("Exception while update entity", e);
        }
    }

    public void updateSubject(Subject subjectForm) throws SchoolDaoException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Subject subject = session.get(Subject.class, subjectForm.getId());
            subject.setSubjectName(subjectForm.getSubjectName());
            session.update(subject);

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
            Student student = session.get(Student.class, studentId);

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
            Subject subject = session.get(Subject.class, studentId);

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

            Query theQuery = session.createQuery(REMOVE_STUDENT);
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
                    session.createQuery(REMOVE_SUBJECT);
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
