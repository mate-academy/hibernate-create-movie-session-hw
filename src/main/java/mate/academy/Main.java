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
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        final MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        final CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie movie = new Movie("Once upon in Hollywood");
        movie.setDescription("An action film about street racing, heists, and spies.22");

        movieService.add(fastAndFurious);
        movieService.add(movie);
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(movieService.get(movie.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall();
        CinemaHall secondHall = new CinemaHall();
        cinemaHall.setCapacity(2);
        secondHall.setCapacity(1000);
        cinemaHall.setDescription("Very small hall");
        secondHall.setDescription("Very dig hall");
        cinemaHallService.add(cinemaHall);
        cinemaHallService.add(secondHall);
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        System.out.println(cinemaHallService.get(secondHall.getId()));
        System.out.println(cinemaHallService.getAll());

        MovieSession session = new MovieSession();
        session.setCinemaHall(cinemaHall);
        session.setMovie(fastAndFurious);
        session.setLocalDateTime(LocalDateTime.now());
        movieSessionService.add(session);
        System.out.println(movieSessionService.get(session.getId()));
        System.out.println(movieSessionService
                .findAvailableSessions(fastAndFurious.getId(),
                        session.getLocalDateTime().toLocalDate()));
    }
}
