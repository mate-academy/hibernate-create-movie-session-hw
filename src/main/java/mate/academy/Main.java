package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.MovieSessionService;

import java.time.LocalDateTime;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        MovieSession movieSession = new MovieSession();
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieSession.setMovie(fastAndFurious);
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(150);
        cinemaHall.setDescription("Great hall with soft chairs");
        movieSession.setCinemaHall(cinemaHall);
        LocalDateTime localDateTime = LocalDateTime.now();
        movieSession.setShowTime(localDateTime);
        movieSessionService.add(movieSession);

    }
}
