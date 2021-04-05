import jpql.Address;
import jpql.Member;
import jpql.MemberDTO;
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

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);


            Member member = new Member();
            member.setUsername("현건수");
            member.setAge(10);
            member.setTeam(team);

            em.persist(member);
            em.flush();
            em.clear();

            String query  = "select m from Member m left join m.team t on t.name='teamA'";
            List<Member> resultList = em.createQuery(query, Member.class)
                    .getResultList();

            resultList.forEach(System.out::println);

            System.out.println("resultList.size() = " + resultList.size());



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