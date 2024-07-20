package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.ShoppingCart;

public interface ShoppingCartDao {
    ShoppingCart add(ShoppingCart shoppingCart);

    Optional<ShoppingCart> get(Long id);

    List<ShoppingCart> getAll();
}
