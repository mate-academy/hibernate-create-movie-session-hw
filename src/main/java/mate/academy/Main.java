package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.model.Order;
import mate.academy.model.ShoppingCart;
import mate.academy.model.Ticket;
import mate.academy.model.User;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;
import mate.academy.service.OrderService;
import mate.academy.service.ShoppingCartService;
import mate.academy.service.TicketService;
import mate.academy.service.UserService;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall middleCinemaHall = new CinemaHall();
        middleCinemaHall.setCapacity(100);
        middleCinemaHall.setDescription("Middle");
        cinemaHallService.add(middleCinemaHall);
        System.out.println(cinemaHallService.get(1L));
        System.out.println(cinemaHallService.getAll());

        MovieSession fastAndFuriousSession = new MovieSession();
        fastAndFuriousSession.setMovie(fastAndFurious);
        fastAndFuriousSession.setCinemaHall(middleCinemaHall);
        fastAndFuriousSession.setShowTime(LocalDateTime.now());
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(fastAndFuriousSession);
        System.out.println(movieSessionService.get(1L));
        System.out.println(movieSessionService.findAvailableSessions(1L, LocalDate.now()));

        UserService userService = (UserService) injector.getInstance(UserService.class);
        User bob = new User();
        bob.setEmail("bob@mail.com");
        bob.setPassword("bob_password");
        userService.add(bob);
        System.out.println(userService.get(1L));
        System.out.println(userService.getAll());

        TicketService ticketService = (TicketService) injector.getInstance(TicketService.class);
        Ticket fastAndFuriousTicket = new Ticket();
        fastAndFuriousTicket.setMovieSession(fastAndFuriousSession);
        fastAndFuriousTicket.setUser(bob);
        ticketService.add(fastAndFuriousTicket);
        System.out.println(ticketService.get(1L));
        System.out.println(ticketService.getAll());

        ShoppingCartService cartService =
                (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        ShoppingCart bobShoppingCart = new ShoppingCart();
        bobShoppingCart.setUser(bob);
        bobShoppingCart.setTickets(List.of(fastAndFuriousTicket));
        cartService.add(bobShoppingCart);
        System.out.println(cartService.get(1L));
        System.out.println(cartService.getAll());

        Order bobOrder = new Order();
        bobOrder.setTickets(List.of(fastAndFuriousTicket));
        bobOrder.setUser(bob);
        bobOrder.setOrderDate(LocalDateTime.now());
        OrderService orderService = (OrderService) injector.getInstance(OrderService.class);
        orderService.add(bobOrder);
        System.out.println(orderService.get(1L));
        System.out.println(orderService.getAll());
    }
}
