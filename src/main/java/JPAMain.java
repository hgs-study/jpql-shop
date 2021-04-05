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

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);


            Member member = new Member();
            member.setUsername("현건수");
            member.setAge(10);
            member.setTeam(team);
            member.setType(MemberType.ADMIN);

            em.persist(member);
            em.flush();
            em.clear();

            String query = "select " +
                                "case when m.age<=10 then '학생 요금'" +
                                "     when m.age>=60 then '경로 요금'" +
                                "     else '일반 요금'" +
                                "end" +
                            " from Member m";
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