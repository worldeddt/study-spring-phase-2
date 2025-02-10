package springstudy.graphqlExample.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateItemDto {
    private Long id;
    private String name;
    private Integer price;
    private Integer stockQuantity;
}
