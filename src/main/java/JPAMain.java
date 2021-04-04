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

            em.flush();
            em.clear();

            //엔티티 프로젝션는 다 영속성 컨텍스트에서 관리함
            List<Member> result = em.createQuery("select m from Member m ", Member.class) // 반환타입 Member
                    .getResultList();

            Member member1 = result.get(0);
            member1.setAge(20);

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