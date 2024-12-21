package springstudy.graphqlExample.domain;

import lombok.Getter;
import springstudy.graphqlExample.entities.OrderItem;

@Getter
public class OrderItemDomain {
    private Long id;
    private ItemDomain item;
    private int orderPrice;
    private int count;

    public OrderItemDomain(OrderItem orderItem) {
        this.id = orderItem.getId();
        this.item = new ItemDomain(orderItem.getItem());
        this.orderPrice = orderItem.getOrderPrice();
        this.count = orderItem.getCount();
    }
}
