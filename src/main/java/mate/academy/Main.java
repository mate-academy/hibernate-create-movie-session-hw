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
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie movie = new Movie("Fast and Furious");
        movie.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(movie);
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
        CinemaHall hall = new CinemaHall();
        hall.setCapacity(60);
        hall.setDescription("Platinum");
        cinemaHallService.add(hall);
        System.out.println(cinemaHallService.getAll());

        LocalDateTime localDateTime = LocalDateTime.now();
        MovieSession movieSession = new MovieSession();
        movieSession.setCinemaHall(hall);
        movieSession.setMovie(movie);
        movieSession.setShowTime(localDateTime);
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);

        System.out.println(movieSessionService
                .findAvailableSessions(movie.getId(), localDateTime.toLocalDate()));
    }
}
