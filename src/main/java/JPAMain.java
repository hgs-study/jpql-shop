import jpql.Member;

import javax.persistence.*;

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

            TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class); // 반환타입 Member
            TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class); // 변환타입 명확 username은 String
            Query query3 = em.createQuery("select m.username, m.age from Member m"); //반환 타입이 명확하지 않음 username ->String , age -> int 이기 때문에

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