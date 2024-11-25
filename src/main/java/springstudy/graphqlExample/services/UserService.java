package springstudy.graphqlExample.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springstudy.graphqlExample.entities.User;
import springstudy.graphqlExample.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("User with id " + id + " not found"));
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User createUser(String name, Integer age) {
        User user = new User();
        user.set(name);
        user.setAge(age);
        return userRepository.save(user);
    }
}
