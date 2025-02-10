package springstudy.graphqlExample.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CancelOrderDto {
    private Long orderId;
}
