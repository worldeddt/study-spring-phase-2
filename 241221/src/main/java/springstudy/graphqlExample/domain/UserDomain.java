package springstudy.graphqlExample.domain;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import springstudy.graphqlExample.controller.dto.CreateUserDto;
import springstudy.graphqlExample.entities.User;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDomain {
    private String username;
    private int age;
    private String email;

    public UserDomain(User user) {
        this.username = user.getUsername();
        this.age = user.getAge();
        this.email = user.getEmail();
    }

    public UserDomain(CreateUserDto createUserDto) {
        this.username = createUserDto.getUsername();
        this.age = createUserDto.getAge();
        this.email = createUserDto.getEmail();
    }
}
