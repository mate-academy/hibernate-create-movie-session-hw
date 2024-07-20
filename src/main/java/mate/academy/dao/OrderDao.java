package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.Order;

public interface OrderDao {
    Order add(Order order);

    Optional<Order> get(Long id);

    List<Order> getAll();
}
