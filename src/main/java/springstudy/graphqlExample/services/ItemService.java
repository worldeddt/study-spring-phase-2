package springstudy.graphqlExample.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springstudy.graphqlExample.controller.dto.CreateItemDto;
import springstudy.graphqlExample.entities.Item;
import springstudy.graphqlExample.entities.Ticket;
import springstudy.graphqlExample.repository.ItemRepository;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item createItem(
            CreateItemDto createItemDto
    ) {
        Ticket ticket = new Ticket();
        ticket.setName(createItemDto.getName());
        ticket.setPrice(createItemDto.getPrice());
        ticket.setStockQuantity(createItemDto.getStockQuantity());
        ticket.setImgPath(createItemDto.getImgPath());
        return itemRepository.save(ticket);
    }

}
