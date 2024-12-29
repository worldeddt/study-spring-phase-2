package article.functional.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class DeepCopyService {

    private Cart shopping_cart;

    public void add_item_to_cart(String name, int price) throws CloneNotSupportedException {
        /*

        ;;;;
         */
        shopping_cart = black_friday_promotion_safe(shopping_cart);
    }

    private Cart black_friday_promotion_safe(Cart shoppingCart) throws CloneNotSupportedException {
         var cartOfCopy = deepCopy(shoppingCart);
         black_friday_promotion(cartOfCopy);
         return deepCopy(cartOfCopy);
    }

    private void black_friday_promotion(Cart cartOfCopy) {
    }

    private Cart deepCopy(Cart shoppingCart) throws CloneNotSupportedException {
        return (Cart) shoppingCart.clone();
    }


    private record Cart(
            String name,
            int price
    ) implements Cloneable {
        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
}
