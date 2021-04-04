import jpql.Member;

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
            member.setUsername("현건수");
            member.setAge(10);
            em.persist(member);

            //메서드 체이닝
            Member singleResult = em.createQuery("select m from Member m where m.username=:username", Member.class) // 반환타입 Member
                    .setParameter("username", "현건수")
                    .getSingleResult();
            System.out.println(singleResult.getUsername());




            System.out.println(singleResult);

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