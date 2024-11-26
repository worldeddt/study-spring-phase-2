package springstudy.graphqlExample.controller.dto;


import lombok.Getter;

@Getter
public class CreateOrderDto {
    private Long userId;
    private Long itemId;
    private int price;
    private int quantity;
}
