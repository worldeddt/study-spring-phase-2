package springstudy.graphqlExample.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDomain {
    private String username;
    private int age;
}
