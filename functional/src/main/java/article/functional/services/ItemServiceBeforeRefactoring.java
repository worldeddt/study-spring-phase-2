package article.functional.services;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceBeforeRefactoring {

    private Cart shopping_cart;
    private Cart shopping_cart_total;
    
    private void add_item_to_car(String name, int price) {
        shopping_cart = add_item(shopping_cart, name, price);

        calc_cart_total();
    }

    private Cart add_item(Cart shopping_cart, String name, int price) {
        return new Cart();
    }

    //main
    private void calc_cart_total() {
        shopping_cart_total = calc_total(shopping_cart);
        set_cart_total_dom();
        update_shipping_icons(shopping_cart);
        update_tax_dom();
    }

    private void update_tax_dom() {
        set_tax_dom(calc_total(shopping_cart_total));
    }

    private void set_tax_dom(Cart shoppingCartTotal) {
    }

    private void update_shipping_icons(Cart shopping_cart) {
        List<Button> buttons = get_buy_buttons_dom();
        for (Button button : buttons) {
            Item item = button.item;
            Cart new_cart = add_item(shopping_cart, item.name, item.price);

            if (gets_free_shipping(new_cart))
                button.show_free_shipping_icon();
            else
                button.hide_free_shipping_icon();

        }
    }

    private boolean gets_free_shipping(Cart newCart) {
        return false;
    }

    private List<Button> get_buy_buttons_dom() {
        return new ArrayList<>();
    }

    private void set_cart_total_dom() {
    }

    private Cart calc_total(Cart shopping_cart) {
        return shopping_cart;
    }

    private record Cart() {

    }

    private record Item(
            String name,
            int price
    ) {
    }

    private record Button(
            Item item
    ) {
        public void show_free_shipping_icon() {

        }

        public void hide_free_shipping_icon() {

        }
    }
}
