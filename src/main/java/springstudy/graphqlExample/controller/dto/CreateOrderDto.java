package springstudy.graphqlExample.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreateOrderDto {
    private Integer userId;
    private List<Integer> itemIds;
    private int price;
    private int quantity;
}
