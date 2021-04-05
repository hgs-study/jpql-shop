import jpql.*;

import javax.persistence.*;
import java.util.List;

public class JPAMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();


        tx.begin();
        try{



            Member member = new Member();
            member.setUsername("관리자"); //nullif
            member.setAge(10);
            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("관리자2"); //nullif
            member2.setAge(20);
            em.persist(member2);

            em.flush();
            em.clear();

            String query = "select function('group_concat',m.username) from Member m";
            List<String> query1 = em.createQuery(query, String.class).getResultList();

            query1.forEach(System.out::println);

            tx.commit();
        }catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }
        emf.close();
    }



}