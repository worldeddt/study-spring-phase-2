package springstudy.graphqlExample.services;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springstudy.graphqlExample.controller.dto.CreateItemDto;
import springstudy.graphqlExample.controller.dto.UpdateItemDto;
import springstudy.graphqlExample.domain.ItemDomain;
import springstudy.graphqlExample.entities.Item;
import springstudy.graphqlExample.entities.Ticket;
import springstudy.graphqlExample.repository.ItemRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item createItem(
            CreateItemDto createItemDto
    ) {

        if (!createItemDto.getType().equals("ticket")) {
            throw new RuntimeException("Unsupported type: " + createItemDto.getType());
        }

        Ticket ticket = new Ticket();
        ticket.setName(createItemDto.getName());
        ticket.setPrice(createItemDto.getPrice());
        ticket.setStockQuantity(createItemDto.getStockQuantity());
        ticket.setImgPath(createItemDto.getImgPath());
        return itemRepository.save(ticket);
    }

    @Transactional
    public Item updateItem(UpdateItemDto updateItemDto) {

        Item item = itemRepository.findById(updateItemDto.getId())
                .orElseThrow(() -> new RuntimeException("item not found"));

        if (updateItemDto.getName() != null) item.setName(updateItemDto.getName());

        if (updateItemDto.getPrice() != null) item.setPrice(updateItemDto.getPrice());

        if (updateItemDto.getStockQuantity() != null) item.setStockQuantity(updateItemDto.getStockQuantity());

        return item;
    }

    public List<ItemDomain> getItems() {
        List<ItemDomain> itemDomains = new ArrayList<>();
        itemRepository.findAll().forEach(item ->
                itemDomains.add(new ItemDomain(item)));

        return itemDomains;
    }

}
