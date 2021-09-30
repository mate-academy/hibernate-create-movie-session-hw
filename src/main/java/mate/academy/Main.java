package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.academy");
    public static final MovieService movieService = (MovieService) injector
            .getInstance(MovieService.class);
    public static final CinemaHallService cinemaHallService = (CinemaHallService) injector
            .getInstance(CinemaHallService.class);
    public static final MovieSessionService movieSessionService = (MovieSessionService) injector
            .getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        System.out.println("* Movie *");
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        System.out.println("* Cinema hall *");
        CinemaHall cinemaHall1 = new CinemaHall(30, "1 cinema hall");
        cinemaHallService.add(cinemaHall1);
        System.out.println(cinemaHallService.get(cinemaHall1.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        System.out.println("* Movie session *");
        MovieSession movieSession = new MovieSession(fastAndFurious, cinemaHall1,
                LocalDateTime.now());
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(movieSession.getId()));

        MovieSession movieSession2 = new MovieSession(fastAndFurious, cinemaHall1,
                LocalDateTime.now().plusDays(2));
        movieSessionService.add(movieSession2);

        System.out.println("Sessions today" + movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDate.now()));
        System.out.println("Sessions in 2 days" + movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDate.now().plusDays(2)));
        System.out.println("Sessions 3 days ago" + movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDate.now().minusDays(3)));
    }
}
