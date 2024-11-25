package springstudy.graphqlExample.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateUserDto {
    private String username;
    private int age;
    private String email;
}
