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

            //이전에 flush 없어도 이전 영속성 컨텍스트 자동 flush
            //이유: 커밋을 하거나 쿼리를 날릴 때 강제 호출(flush)된다.
            int resultCount = em.createQuery("update Member m set m.age = 20")
                    .executeUpdate();

            //영속성 컨텍스트 초기화
            em.clear();

            Member findMember1 = em.find(Member.class, member.getId());
            //영속성 컨텍스트 초기화 후 다시 조회해서 20이 나온다.
            System.out.println("findMember1 = " + findMember1.getAge());


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