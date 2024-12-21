package springstudy.graphqlExample.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import springstudy.graphqlExample.domain.ItemDomain;
import springstudy.graphqlExample.domain.OrderDomain;
import springstudy.graphqlExample.domain.UserDomain;
import springstudy.graphqlExample.services.ItemService;
import springstudy.graphqlExample.services.OrderService;
import springstudy.graphqlExample.services.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class QueryController {
    private final UserService userService;
    private final ItemService itemService;
    private final OrderService orderService;

    @QueryMapping
    public UserDomain getUserById(@Argument Long id) {
        return userService.getUserById(id);
    }

    @QueryMapping
    public List<UserDomain> getUsers() {
        return userService.getUsers();
    }

    @QueryMapping
    public List<ItemDomain> getItems() {
        return itemService.getItems();
    }

    @QueryMapping
    public List<OrderDomain> getOrders() {
        return orderService.getOrders();
    }
}
