package mate.academy.service;

import java.util.List;
import mate.academy.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCart add(ShoppingCart shoppingCart);

    ShoppingCart get(Long id);

    List<ShoppingCart> getAll();
}
