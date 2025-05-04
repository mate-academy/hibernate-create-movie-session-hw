package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate");
    private static MovieService movieService = (MovieService) injector
            .getInstance(MovieService.class);
    private static CinemaHallService cinemaHallService = (CinemaHallService) injector
            .getInstance(CinemaHallService.class);
    private static MovieSessionService movieSessionService = (MovieSessionService) injector
            .getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(30);
        cinemaHall.setDescription("Gold");
        cinemaHallService.add(cinemaHall);

        Movie movie = new Movie();
        movie.setTitle("Spider man");
        movie.setDescription("rating 10");
        movieService.add(movie);

        LocalDateTime currentDateTime = LocalDateTime.now();

        MovieSession movieSession = new MovieSession(movie, cinemaHall, currentDateTime);
        movieSessionService.add(movieSession);

        LocalDateTime futureDateTime = LocalDateTime.of(2023, Month.MAY, 6, 18, 33);

        MovieSession movieSessionSecond = new MovieSession(movie, cinemaHall, futureDateTime);
        movieSessionService.add(movieSessionSecond);

        LocalDate date = LocalDate.of(2023, Month.MAY, 7);

        movieSessionService.findAvailableSessions(movie.getId(), date).forEach(System.out::println);
    }
}
