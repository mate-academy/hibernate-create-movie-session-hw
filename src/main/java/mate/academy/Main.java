package mate.academy;

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

    public static void main(String[] args) {
        MovieService movieService = (MovieService)
                injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        CinemaHall greenHall = new CinemaHall();
        greenHall.setCapacity(40);
        greenHall.setDescription("Green hall, lovely to watch movies with your half");

        CinemaHall redHall = new CinemaHall();
        redHall.setCapacity(50);
        redHall.setDescription("Red hall");

        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(greenHall);
        cinemaHallService.add(redHall);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(redHall);
        movieSession.setShowtime(LocalDateTime.now());

        MovieSession fastAndFuriousSession = new MovieSession();
        fastAndFuriousSession.setMovie(fastAndFurious);
        fastAndFuriousSession.setCinemaHall(greenHall);
        fastAndFuriousSession.setShowtime(LocalDateTime.now());

        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        movieSessionService.add(fastAndFuriousSession);
        movieSessionService.add(movieSession);

        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        System.out.println(movieSessionService.getAll());
        System.out.println(movieSessionService
                .findAvailableSessions(1L, LocalDateTime.now().toLocalDate()));
    }
}
