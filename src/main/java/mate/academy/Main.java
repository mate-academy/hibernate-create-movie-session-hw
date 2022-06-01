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
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static MovieService movieService = (MovieService)
            injector.getInstance(MovieService.class);
    private static MovieSessionService movieSessionService = (MovieSessionService)
            injector.getInstance(MovieSessionService.class);
    private static CinemaHallService cinemaHallService = (CinemaHallService)
            injector.getInstance(CinemaHallService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHallAtlanta = new CinemaHall();
        cinemaHallAtlanta.setDescription("A large cinema hall with 80 persons capacity");
        cinemaHallAtlanta.setCapacity(80);
        cinemaHallService.add(cinemaHallAtlanta);
        System.out.println(cinemaHallService.get(cinemaHallAtlanta.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSessionNOne = new MovieSession();
        movieSessionNOne.setMovie(fastAndFurious);
        movieSessionNOne.setCinemaHall(cinemaHallAtlanta);
        movieSessionNOne.setShowTime(LocalDateTime.now());
        movieSessionService.add(movieSessionNOne);
        System.out.println(movieSessionService.get(movieSessionNOne.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.now());
    }
}
