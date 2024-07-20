package mate.academy.service.impl;

import java.util.List;
import mate.academy.dao.ShoppingCartDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.ShoppingCart;
import mate.academy.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Inject
    private ShoppingCartDao shoppingCartDao;

    @Override
    public ShoppingCart add(ShoppingCart shoppingCart) {
        return shoppingCartDao.add(shoppingCart);
    }

    @Override
    public ShoppingCart get(Long id) {
        return shoppingCartDao.get(id).orElseThrow(
                () -> new RuntimeException("Can't find in DB shopping cart by id = " + id)
        );
    }

    @Override
    public List<ShoppingCart> getAll() {
        return shoppingCartDao.getAll();
    }
}
