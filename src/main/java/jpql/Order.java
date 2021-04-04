package jpql;

import javax.persistence.*;

@Entity
@Table(name="ORDERS") //ORDER는 약어라서 ORDERS로 변경
public class Order {

    @Id @GeneratedValue
    private Long id;
    private int orederAmount;
    @Embedded
    private Address address;

    @ManyToOne
    @JoinColumn(name="PRODUCT_ID")
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrederAmount() {
        return orederAmount;
    }

    public void setOrederAmount(int orederAmount) {
        this.orederAmount = orederAmount;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
