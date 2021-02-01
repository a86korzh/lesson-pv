import com.korzh86a.lessons.pv.entity.Mark;
import com.korzh86a.lessons.pv.entity.Student;
import com.korzh86a.lessons.pv.entity.Subject;
import com.korzh86a.lessons.pv.entity.SubjectWithMarks;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Random;

public class FillTestDB {
    SessionFactory sessionFactory;

    public FillTestDB() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Student.class)
                .addAnnotatedClass(Subject.class)
                .addAnnotatedClass(Mark.class)
                .addAnnotatedClass(SubjectWithMarks.class)
                .configure("hibernate.cfg.xml");

        ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        sessionFactory = configuration.buildSessionFactory(serviceRegistryObj);
    }

    public static void main(String[] args) {
        FillTestDB fillTestDB = new FillTestDB();

        Session session = fillTestDB.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Random random = new Random();
        int num;

        for (int i = 0; i < 10_000; i++) {
            Student student = new Student();
            student.setFirstName("Name " + i);
            student.setSecondName("SurName " + i);

            num = 75 + random.nextInt(85 - 75);
            student.setBirthDate("19" + num + "-05-09");

            num = 92 + random.nextInt(100 - 92);
            student.setEnterYear("19" + num);

            session.save(student);
        }

        for (int i = 0; i < 10_000; i++) {
            for (int j = 0; j < 5; j++) {
                SubjectWithMarks subjectWithMarks = new SubjectWithMarks();

                Student student = new Student();
                student.setId(i + 1);
                subjectWithMarks.setStudent(student);

                Subject subject = new Subject();
                subject.setId(j + 1);
                subjectWithMarks.setSubject(subject);

                session.save(subjectWithMarks);
            }
        }

        for (int i = 0; i < 50_000; i++) {
            num = 1 + random.nextInt(11 - 1);
            Mark mark = new Mark(num);

            SubjectWithMarks subjectWithMarks = new SubjectWithMarks();
            subjectWithMarks.setId(i + 1);
            mark.setSubjectWithMarks(subjectWithMarks);

            session.save(mark);
        }

        transaction.commit();
    }
}
