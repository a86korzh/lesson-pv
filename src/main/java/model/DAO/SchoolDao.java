package model.DAO;

import model.DTO.MarkDto;
import model.DTO.StudentDto;
import model.DTO.SubjectDto;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchoolDao implements AutoCloseable{
    private SessionFactory sessionFactory;

    public SchoolDao() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(StudentDto.class)
                .addAnnotatedClass(SubjectDto.class)
                .addAnnotatedClass(MarkDto.class)
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

    public List<StudentDto> getAllStudents() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<StudentDto> students = session.createQuery("FROM StudentDto").list();
        transaction.commit();
        if (session!=null) {
            session.close();
        }
        return students;
    }

    public StudentDto getStudent(int studentId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        StudentDto student = session.get(StudentDto.class, studentId);
        transaction.commit();
        if (session!=null) {
            session.close();
        }
        return student;
    }

    public Map<SubjectDto, List<MarkDto>> getStudentMarks(StudentDto studentDto) {
        Map<SubjectDto, List<MarkDto>> map = new HashMap<>();

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("FROM MarkDto WHERE studentId = :studentId");
        query.setParameter("studentId", studentDto.getId());
        List<MarkDto> marks = query.list();

        SubjectDto subject;
        List<MarkDto> temp;

        for (MarkDto m : marks) {
            subject = session.get(SubjectDto.class, m.getSubjectId());

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

    public void removeStudent(StudentDto student) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("DELETE FROM MarkDto WHERE studentId = :studentId");
        query.setParameter("studentId", student.getId());
        query.executeUpdate();

        session.delete(student);
        transaction.commit();
        if (session!=null) {
            session.close();
        }
    }

    public List<SubjectDto> getAllSubjects() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<SubjectDto> subjects = session.createQuery("FROM SubjectDto").list();
        transaction.commit();
        if (session!=null) {
            session.close();
        }
        return subjects;
    }

    public SubjectDto getSubject(int studentId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        SubjectDto subject = session.get(SubjectDto.class, studentId);
        transaction.commit();
        if (session!=null) {
            session.close();
        }
        return subject;
    }

    public void removeSubjectById(int subjectId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("DELETE FROM MarkDto WHERE subjectId = :subjectId");
        query.setParameter("subjectId", subjectId);
        query.executeUpdate();

        SubjectDto subject = session.get(SubjectDto.class, subjectId);
        session.delete(subject);
        transaction.commit();
        if (session!=null) {
            session.close();
        }
    }

    public MarkDto getMark(int markId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        MarkDto mark = session.get(MarkDto.class, markId);
        transaction.commit();
        if (session!=null) {
            session.close();
        }
        return mark;
    }

    public void removeMarkById(int markId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        MarkDto mark = session.get(MarkDto.class, markId);
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
