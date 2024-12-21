package springstudy.graphqlExample.services;


import jakarta.persistence.EntityManager;
import jakarta.transaction.TransactionManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import springstudy.graphqlExample.controller.dto.CancelOrderDto;
import springstudy.graphqlExample.controller.dto.CreateOrderDto;
import springstudy.graphqlExample.domain.ItemDomain;
import springstudy.graphqlExample.domain.OrderDomain;
import springstudy.graphqlExample.entities.Item;
import springstudy.graphqlExample.entities.Order;
import springstudy.graphqlExample.entities.OrderItem;
import springstudy.graphqlExample.entities.User;
import springstudy.graphqlExample.enums.OrderStatus;
import springstudy.graphqlExample.repository.ItemRepository;
import springstudy.graphqlExample.repository.OrderItemRepository;
import springstudy.graphqlExample.repository.OrderRepository;
import springstudy.graphqlExample.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static springstudy.graphqlExample.entities.Order.saveOrder;
import static springstudy.graphqlExample.entities.OrderItem.createOrderItem;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public List<OrderDomain> getOrders() {
        List<OrderDomain> orderDomains = new ArrayList<>();
        orderRepository.findAll().forEach(orders ->
                orderDomains.add(new OrderDomain(orders)));

        return orderDomains;
    }

    @Transactional(rollbackOn = Exception.class)
    public Order createOrder(CreateOrderDto createOrderDto) {

        User findUser = null;
        List<OrderItem> orderItems = new ArrayList<>();

        try {
            for (Integer itemId : createOrderDto.getItemIds()) {
                Item item = itemRepository.findById((long) itemId)
                        .orElseThrow(() -> new RuntimeException("item not found"));

                orderItems.add(createOrderItem(
                        item,
                        createOrderDto.getPrice(),
                        createOrderDto.getQuantity()
                ));
            }

            findUser = userRepository.findById((long) createOrderDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("user not found"));

            log.debug("1. find user : {}", findUser.getId());

            Order order = saveOrder(
                    findUser,
                    orderItems
            );

            orderRepository.save(order);

            log.debug("1. find order : {}", order.getId());
            return order;
        } catch (RuntimeException e) {

            log.error(e.getMessage());

            return null;
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public Order cancelOrder(CancelOrderDto cancelOrderDto) {
        try  {
            Order order = orderRepository.findById(cancelOrderDto.getOrderId())
                    .orElseThrow(() -> new RuntimeException("order not found"));

            order.setStatus(OrderStatus.CANCEL);

            order.getOrderItems().forEach(orderItem -> {
                orderItem.cancel();
            });

            return order;
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
