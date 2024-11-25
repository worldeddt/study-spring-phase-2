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
        UserDomain.builder()
                .age(user.getAge())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }

    public UserDomain(CreateUserDto createUserDto) {
        UserDomain.builder()
                .age(createUserDto.getAge())
                .email(createUserDto.getEmail())
                .username(createUserDto.getUsername())
                .build();
    }
}
