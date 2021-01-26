import com.korzh86a.lessons.pv.entity.SubjectWithMarks;
import com.korzh86a.lessons.pv.entity.Mark;
import com.korzh86a.lessons.pv.entity.Student;
import com.korzh86a.lessons.pv.entity.Subject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

public class Main {
    SessionFactory sessionFactory;

    public Main() {
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
        Main main = new Main();
        Session session = main.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
//        List<SubjectWithMarks> result = session.createQuery("FROM SubjectWithMarks order by id").list();
//        List<Subject> result = session.createQuery("FROM Subject order by id").list();
//        List<Student> result = session.createQuery("FROM Student order by id").list();
        Student result = session.get(Student.class, 1);
        transaction.commit();

        System.out.println(result.getSubjectsWithMarks());
//        result.forEach(System.out::println);
    }
}
