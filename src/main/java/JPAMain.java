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


            String query = "select m from Member m";

            List<Member> queryList = em.createQuery(query, Member.class).getResultList();

            for (Member member1 : queryList) {
                System.out.println("member = " + member1.getUsername() +","+member1.getTeam().getName());
                // 회원1, 팀A (SQL)
                // 회원2, 팀A (1차캐시)
                // 회원3, 팀B (SQL)

                // 회원100명이 다 다를경우 => N + 1 쿼리가 나감 (결과 TEAM N개, MEMBER 1개)
                // 즉시 로딩하든 지연로딩하든 발생함 (해결 -> 페치조인)
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