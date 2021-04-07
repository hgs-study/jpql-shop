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
            Team teamA = new Team();
            teamA.setName("A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("B");
            em.persist(teamB);

            Member member = new Member();
            member.setUsername("회원1"); //nullif
            member.setTeam( teamA);
            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("회원2"); //nullif
            member2.setTeam(teamA);
            em.persist(member2);
            
            Member member3 = new Member();
            member3.setUsername("회원2"); //nullif
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();


            String query = "select m from Member m join fetch m.team";

            List<Member> queryList = em.createQuery(query, Member.class).getResultList();

            for (Member member1 : queryList) {
                System.out.println("member = " + member1.getUsername() +","+member1.getTeam().getName());
                //member1.getTeam()의  team은 프록시 객체가 아니라 진짜 객체임
                //이유 : join fetch로 인해 쿼리문 하나로 한 번에 다 가져오기 때문에
            }

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