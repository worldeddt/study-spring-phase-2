package springstudy.graphqlExample.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@DiscriminatorValue(value = "ticket")  // 구분컬럼명 지정
@Entity(name = "ticket")
@Getter
@Setter
public class Ticket extends Item {

    private String imgPath;
}