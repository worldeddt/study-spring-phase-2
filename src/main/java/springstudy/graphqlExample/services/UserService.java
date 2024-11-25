package springstudy.graphqlExample.services;


import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import springstudy.graphqlExample.controller.dto.CreateUserDto;
import springstudy.graphqlExample.domain.UserDomain;
import springstudy.graphqlExample.entities.User;
import springstudy.graphqlExample.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDomain getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("User with id " + id + " not found"));

        return new UserDomain(user);
    }

    public List<UserDomain> getUsers() {

        List<UserDomain> userDomains = new ArrayList<>();
        userRepository.findAll().forEach(user -> userDomains.add(new UserDomain(user)));

        return userDomains;
    }

    public User createUser(
            CreateUserDto createUserDto
    ) {
        User user = new User();
        log.debug("create user dto : {}", new Gson().toJson(createUserDto));
        user.setUser(new UserDomain(createUserDto));

        return userRepository.save(user);
    }
}
