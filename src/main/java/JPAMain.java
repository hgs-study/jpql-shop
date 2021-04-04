import jpql.Address;
import jpql.Member;
import jpql.Team;

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

            em.flush();
            em.clear();

            //스칼라 프로젝션은 sql짜듯이 내가 가지고 싶어오고 싶은 것 가져옴
            em.createQuery("select distinct m.username,m.age from Member m ") // 반환타입 Member
                    .getResultList();


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