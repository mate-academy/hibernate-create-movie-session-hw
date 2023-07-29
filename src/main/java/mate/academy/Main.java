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
    private static final Injector injector = Injector.getInstance("mate");

    public static void main(String[] args) {
        final MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        final CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setDescription("Berlin cinema hall");
        cinemaHall.setCapacity(2000);
        cinemaHallService.add(cinemaHall);
        CinemaHall secondCinemaHall = new CinemaHall();
        secondCinemaHall.setDescription("Antwerp cinema hall");
        secondCinemaHall.setCapacity(500);
        cinemaHallService.add(secondCinemaHall);
        System.out.println(cinemaHallService.get(2L));
        System.out.println(cinemaHallService.getAll());

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(movieService.get(1L));
        movieSession.setCinemaHall(cinemaHallService.get(1L));
        movieSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(movieSession);
        movieSession.setCinemaHall(cinemaHallService.get(2L));
        movieSession.setShowTime(LocalDateTime.now().plusHours(4));
        movieSessionService.add(movieSession);

        System.out.println(movieSessionService.get(1L));
        System.out.println(movieSessionService.findAvailableSessions(1L,
                LocalDate.now()));
    }
}
