package springstudy.jpa.entities;


import jakarta.persistence.*;
import lombok.Getter;

@Table(name = "order")
@Entity
@Getter
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}