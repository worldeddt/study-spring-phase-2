package springstudy.graphqlExample.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import springstudy.graphqlExample.controller.dto.CreateItemDto;
import springstudy.graphqlExample.controller.dto.CreateUserDto;
import springstudy.graphqlExample.entities.Item;
import springstudy.graphqlExample.entities.User;
import springstudy.graphqlExample.services.ItemService;
import springstudy.graphqlExample.services.UserService;

@Controller
@RequiredArgsConstructor
public class MutationController {

    private final UserService userService;
    private final ItemService itemService;

    @MutationMapping
    public User createUser(
            @Argument String username, @Argument Integer age, @Argument String email
    ) {

        return userService.createUser(
            new CreateUserDto(username, age, email)
        );
    }

    @MutationMapping
    public Item createItem(
            @Argument("input") CreateItemDto dto
    ) {
        return itemService.createItem(dto);
    }
}