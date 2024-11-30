package lab;

import lab.entities.JPAStudent;
import lab.entities.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        hibernate();
        jpa();
    }

    private static void jpa() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("school");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        JPAStudent student = new JPAStudent();
        student.setName("Gosho ot JPA");
        em.persist(student);

        JPAStudent goshoToBeRemoved = em.find(JPAStudent.class, 1L);
        JPAStudent gosho = em.find(JPAStudent.class, 3L);
        System.out.println(gosho.getName());
        em.remove(goshoToBeRemoved);
        gosho.setName("Promenen Gosho");
        em.merge(gosho);
        em.getTransaction().commit();
    }

    private static void hibernate() {
        Configuration cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        createAndPrintStudents(session);
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery query = cb.createQuery();
        Root<Student> root = query.from(Student.class);
        query.select(root).where(cb.like(root.get("name"), "P%"));

        List<Student> students = session.createQuery(query).getResultList();

        students.forEach(s -> System.out.println(s.getName()));

        session.getTransaction().commit();
        session.close();
    }

    private static void createAndPrintStudents(Session session) {
        Student gosho = new Student();
        gosho.setName("Gosho");
        Student pesho = new Student();
        pesho.setName("Pesho");
        session.save(gosho);
        session.save(pesho);
        gosho = new Student();
        gosho.setName("Gosho");
        pesho = new Student();
        pesho.setName("Pesho");
        session.save(gosho);
        session.save(pesho);


        pesho = session.get(Student.class, 2L);

        System.out.println(pesho.getName());

        List<Student> students = session
                .createQuery("FROM Student ", Student.class)
                .list();

        students.forEach(s -> System.out.println(s.getId() + " -> " + s.getName()));
    }
}
