package springstudy.graphqlExample.domain;

import lombok.Getter;
import springstudy.graphqlExample.entities.Order;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderDomain {
    private Long id;
    private Long userId;
    private List<OrderItemDomain> orderItemDomains;
    private String orderStatus;

    public OrderDomain(Order order) {
        this.id = order.getId();
        this.userId = order.getUser().getId();

        List<OrderItemDomain> orderItemDomains = new ArrayList<>();
        order.getOrderItems().forEach(orderItem -> {
            assert orderItemDomains != null;
            orderItemDomains.add(new OrderItemDomain(orderItem));
        });

        this.orderItemDomains = orderItemDomains;
        this.orderStatus = order.getStatus().toString();
    }
}
