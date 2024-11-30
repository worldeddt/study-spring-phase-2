package springstudy.graphqlExample.services;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springstudy.graphqlExample.controller.dto.CreateOrderDto;
import springstudy.graphqlExample.entities.Item;
import springstudy.graphqlExample.entities.Order;
import springstudy.graphqlExample.entities.OrderItem;
import springstudy.graphqlExample.entities.User;
import springstudy.graphqlExample.repository.ItemRepository;
import springstudy.graphqlExample.repository.OrderItemRepository;
import springstudy.graphqlExample.repository.OrderRepository;
import springstudy.graphqlExample.repository.UserRepository;

import java.util.List;

import static springstudy.graphqlExample.entities.Order.saveOrder;
import static springstudy.graphqlExample.entities.OrderItem.createOrderItem;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Transactional
    public Order createOrderOne(CreateOrderDto createOrderDto) {

        Item item = itemRepository.findById((long) createOrderDto.getItemId())
                .orElseThrow(() -> new RuntimeException("item not found"));

        OrderItem orderItem = createOrderItem(
                item,
                createOrderDto.getPrice(),
                createOrderDto.getQuantity()
        );

//        OrderItem saveOrderItem = orderItemRepository.save(
//                createOrderItem(
//                        item,
//                        createOrderDto.getPrice(),
//                        createOrderDto.getQuantity()
//                )
//        );
//
//        OrderItem orderItem =
//                orderItemRepository.findById(saveOrderItem.getId())
//                .orElseThrow(() -> new RuntimeException("order item not found"));

        User findUser = userRepository.findById((long) createOrderDto.getItemId())
                .orElseThrow(() -> new RuntimeException("user not found"));

        return saveOrder(
                findUser,
                List.of(orderItem)
        );
    }
}
