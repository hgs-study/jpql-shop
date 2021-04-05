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
            for(int i = 0; i<100 ; i++){
                Member member = new Member();
                member.setUsername("member "+i);
                member.setAge(i);
                em.persist(member);
            }

            em.flush();
            em.clear();

            List<Member> resultList = em.createQuery("select m from Member m order by m.age desc ", Member.class)
                    .setFirstResult(1) //인덱스
                    .setMaxResults(10) // 몇개 가져올거야?!
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