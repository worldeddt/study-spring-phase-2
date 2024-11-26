package springstudy.graphqlExample.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springstudy.graphqlExample.enums.OrderStatus;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(
            mappedBy = "order",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    private List<OrderItem> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)  // ENUM 클래스 값 형식 설정
    private OrderStatus status;  // ORDER, CANCEL

    // 연관관계 편의 메소드
    public void setUser(User user) {
        this.user = user;
        user.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    // 생성 메소드
    public static Order createOrder(
            User user, OrderItem...orderItems
    ) {
        Order order = new Order();
        order.setUser(user);

        for(OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        order.setStatus(OrderStatus.ORDER);
        return order;
    }

    /**
     * 전체 주문 가격 조회
     * @return
     */
    public int getTotalPrice() {
        int totalPrice = 0;

        for(OrderItem orderItem : this.orderItems) {
            totalPrice += orderItem.getTotalPrice();    // 상품 가격 누적
        }

        return totalPrice;
    }

}