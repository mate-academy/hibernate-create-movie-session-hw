package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall hegemon = new CinemaHall();
        hegemon.setCapacity(200);
        hegemon.setDescription("Biggest cinema hall in the cinema");
        cinemaHallService.add(hegemon);
        System.out.println(cinemaHallService.get(hegemon.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(hegemon);
        movieSession.setShowTime(LocalDateTime.of(2023, Month.NOVEMBER, 13, 13, 20));
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(movieSession.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2023, Month.NOVEMBER, 13));
    }
}
