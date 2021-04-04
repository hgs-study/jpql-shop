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
            Member member = new Member();
            member.setUsername("현건수");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            //DTO로 뽑아낼 때는 new jpql.MemberDTO(m.username,m.age)로 작성
            //나중에 쿼리DSL 사용하면 이 부분도 극복이 됨
            List<MemberDTO> resultList = em.createQuery("select new jpql.MemberDTO(m.username,m.age) from Member m ", MemberDTO.class) // 반환타입 Member
                    .getResultList();

            MemberDTO memberDTO = resultList.get(0);
            System.out.println(memberDTO.getUsername());
            System.out.println(memberDTO.getAge());

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