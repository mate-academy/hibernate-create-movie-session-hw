package mate.academy.service;

import java.util.List;
import mate.academy.model.Order;

public interface OrderService {
    Order add(Order order);

    Order get(Long id);

    List<Order> getAll();
}
