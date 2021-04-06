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

            //상태 필드
            //m.username
            String query = "select m.username from Member m";

            //단일 연관 경로 (묵시적 내부 조인 inner join)발생
            //m.team.name
            String query2 = "select m.team.name from Member m";

            List<String> queryList = em.createQuery(query, String.class).getResultList();
            List<String> query2List = em.createQuery(query2, String.class).getResultList();

            queryList.forEach(System.out::println);

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