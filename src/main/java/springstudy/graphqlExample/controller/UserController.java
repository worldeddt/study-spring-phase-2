package springstudy.graphqlExample.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import springstudy.graphqlExample.domain.UserDomain;
import springstudy.graphqlExample.services.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @QueryMapping
    public UserDomain getUserById(@Argument Long id) {
        return userService.getUserById(id);
    }

    @QueryMapping
    public List<UserDomain> getUsers() {
        return userService.getUsers();
    }

}
