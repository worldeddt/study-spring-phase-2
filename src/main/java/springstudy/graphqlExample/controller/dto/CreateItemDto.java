package springstudy.graphqlExample.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateItemDto {
    private String name;
    private int price;
    private int stockQuantity;
    private String type;
    private String imgPath;
}