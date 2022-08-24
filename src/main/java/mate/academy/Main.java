package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        Movie terminator = new Movie();
        terminator.setTitle("Terminator");
        terminator.setDescription("Thriller");

        CinemaHall hallOne = new CinemaHall();
        hallOne.setDescription("Hall 1");
        hallOne.setCapacity(50);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(terminator);
        movieSession.setCinemaHall(hallOne);
        movieSession.setShowTime(LocalDateTime.of(LocalDate.of(2022, 9, 1),
                LocalTime.of(21, 0))
        );

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.findAvailableSessions(terminator.getId(),
                LocalDate.of(2022, 9, 1)));

        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        System.out.println(cinemaHallService.getAll());

        System.out.println(movieService.getAll());
    }
}
