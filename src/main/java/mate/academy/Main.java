package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.MovieService;
import mate.academy.service.impl.CinemaHallService;
import mate.academy.service.impl.MovieSessionService;

public class Main {
    private static final Injector injector =
            Injector.getInstance("mate.academy");

    public static void main(String[] args) {

        MovieService movieService =
                (MovieService)injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie();
        fastAndFurious.setTitle("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService)injector.getInstance(CinemaHallService.class);
        CinemaHall cinemaHallMultiplex = new CinemaHall();
        cinemaHallMultiplex.setDescription("Multiplex");
        cinemaHallMultiplex.setCapacity(130);
        cinemaHallService.add(cinemaHallMultiplex);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(movieService.get(1L));
        movieSession.setCinemaHall(cinemaHallMultiplex);
        movieSession.setShowTime(LocalDateTime.of(2023, 5, 15, 20, 15));

        MovieSessionService movieSessionService =
                (MovieSessionService)injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        movieSessionService.findAvailableSessions(1L, LocalDate.of(2023, 5,15))
                .forEach(System.out::println);
    }
}
