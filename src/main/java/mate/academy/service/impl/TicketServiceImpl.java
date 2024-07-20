package mate.academy.service.impl;

import java.util.List;
import mate.academy.dao.TicketDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.Ticket;
import mate.academy.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {
    @Inject
    private TicketDao ticketDao;

    @Override
    public Ticket add(Ticket ticket) {
        return ticketDao.add(ticket);
    }

    @Override
    public Ticket get(Long id) {
        return ticketDao.get(id).orElseThrow(
                () -> new RuntimeException("Can't find in DB ticket by id = " + id)
        );
    }

    @Override
    public List<Ticket> getAll() {
        return ticketDao.getAll();
    }
}
