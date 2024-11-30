package springstudy.graphqlExample.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateOrderDto {
    private int userId;
    private int itemId;
    private int price;
    private int quantity;
}
