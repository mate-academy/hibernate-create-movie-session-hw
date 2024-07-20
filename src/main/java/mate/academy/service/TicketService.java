package mate.academy.service;

import java.util.List;
import mate.academy.model.Ticket;

public interface TicketService {
    Ticket add(Ticket ticket);

    Ticket get(Long id);

    List<Ticket> getAll();
}
