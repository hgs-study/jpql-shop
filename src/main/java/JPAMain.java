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


            String query = "select DISTINCT t from Team t";

            //DB엔 여러 ROW가 있는데 PAGE 1개만 가져와 하면 JPA는 그대로 1개만 가져온다.
            //그 1개에 객체 그래프를 다 가져오는 속성때문에 여러 객체(ROW)를 다 가져온다.
            List<Team> queryList = em.createQuery(query, Team.class)
                    .setFirstResult(0)
                    .setMaxResults(2)
                    .getResultList();

            System.out.println("queryList.size() = " + queryList.size());

            //컬렉션 페치조인 일대다에선 데이터 증폭돼서 나옴
            //이유 : teamA에 2명의 회원이 있으면 똑같은 데이터가 2개나옴 (회원이 2명이기 때문에 2줄이 나오는 것)
            for (Team team : queryList) {
                System.out.println("team.getName() = " + team.getName() +", members = "+team.getMembers().size());
                for (Member teamMember : team.getMembers()) {
                    System.out.println("-> teamMember = " + teamMember);
                }
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