package article.functional.services;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LayerService {

    private List<Cart> carts;

    public List<Cart> freeTieClip(List<Cart> carts) {
        var hasTie = isInCart(carts, "tie");
        var hasTieClip = isInCart(carts, "tie clip");

        if (hasTie && !hasTieClip) {
            var tieClip = make_item("tie clip", 0);
            return add_item(carts, tieClip);
        }

        return carts;
    }

    public List<Cart> remove_item_by_name(List<Cart> carts, String name) {
        Integer index = null;
        for (int i = 0; i < carts.size(); i++) {
            if (carts.get(i).name.equals(name)) {
                index = i;
            }
        }

        if (index != null) return remove_item(carts, index);

        return carts;
    }

    private List<Cart> remove_item(List<Cart> carts,  int index) {
        carts.remove(carts.get(index));
        return carts;
    }

    private boolean isInCart(List<Cart> carts, String tieClip) {

        for (Cart cart : carts) {
            if (cart.name.equals(tieClip)) {
                return true;
            }
        }

        return false;
    }

    private record Cart(
            String name,
            int price
    ) {

    }

    private List<Cart> add_item(List<Cart> carts, Object tieClip) {
        return null;
    }

    private Object make_item(String tieClip, int i) {
        return null;
    }
}
