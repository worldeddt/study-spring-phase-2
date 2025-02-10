package article.functional.services;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UnChangableService {

    //카피 온 라이트 적용 전
    public void remove_item_by_name(List<Cart> carts, String name) {
        for (Cart cart : carts) {
            if (cart.name.equals(name)) {
                carts.remove(cart);
            }
        }
    }

    //카피 온 라이트 적용 후
    public List<Cart> refactored_remove_item_by_name(List<Cart> carts, String name) {
        List<Cart> copyOfCart = carts;

        for (Cart cart : copyOfCart) {
            if (cart.name.equals(name)) {
                carts.remove(cart);
            }
        }
       return copyOfCart;
    }

    private record Cart(
            String name
    ) {

    }
}
