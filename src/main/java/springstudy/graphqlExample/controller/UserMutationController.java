package springstudy.graphqlExample.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import springstudy.graphqlExample.controller.dto.CreateUserDto;
import springstudy.graphqlExample.entities.User;
import springstudy.graphqlExample.services.UserService;

@Controller
@RequiredArgsConstructor
public class UserMutationController {

    private final UserService userService;

    @MutationMapping
    public User createUser(
            @Argument String username, @Argument Integer age, @Argument String email
    ) {

        return userService.createUser(
            new CreateUserDto(username, age, email)
        );
    }
}