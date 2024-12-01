package springstudy.graphqlExample.domain;

import lombok.Getter;
import lombok.Setter;
import springstudy.graphqlExample.entities.Order;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderDomain {
    private Long id;
    private Long userId;
    private List<Long> orderItemId;
    private String orderStatus;

    public OrderDomain(Order order) {
        this.id = order.getId();
        this.userId = order.getUser().getId();

        List<Long> orderItemId = new ArrayList<>();
        order.getOrderItems().forEach(orderItem -> {
            assert orderItemId != null;
            orderItemId.add(orderItem.getId());
        });

        this.orderItemId = orderItemId;
        this.orderStatus = order.getStatus().toString();
    }
}
