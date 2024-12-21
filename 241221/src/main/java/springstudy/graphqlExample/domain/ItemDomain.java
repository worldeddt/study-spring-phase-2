package springstudy.graphqlExample.domain;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import springstudy.graphqlExample.controller.dto.CreateItemDto;
import springstudy.graphqlExample.entities.Item;


@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemDomain {
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    public ItemDomain(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.stockQuantity = item.getStockQuantity();
    }

    public ItemDomain(CreateItemDto dto) {
        this.name = dto.getName();
        this.price = dto.getPrice();
        this.stockQuantity = dto.getStockQuantity();
    }
}
