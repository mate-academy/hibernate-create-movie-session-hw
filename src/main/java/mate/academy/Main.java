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
        MovieService movieService = (MovieService) Injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        CinemaHallService cinemaHallService =
                (CinemaHallService) Injector.getInstance(CinemaHallService.class);
        CinemaHall imax = new CinemaHall();
        imax.setCapacity(40);
        imax.setDescription("Hall with imax");
        cinemaHallService.add(imax);

        MovieSessionService movieSessionService =
                (MovieSessionService) Injector.getInstance(MovieSessionService.class);
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(imax);
        movieSession.setShowTime(LocalDateTime.of(2024, 10, 10, 15, 30));
    }
}
