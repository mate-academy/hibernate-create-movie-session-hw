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

    public static void main(String[] args) {
        final MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        final CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie theDeparted = new Movie("The Departed");
        theDeparted.setDescription("In this crime-action tour de force, the South Boston state "
                + "police force is waging war on Irish-American organized crime");
        CinemaHall cinemaHallBig = new CinemaHall();
        cinemaHallBig.setCapacity(200);
        cinemaHallBig.setDescription("Big cinema hall");
        CinemaHall cinemaHallMedium = new CinemaHall();
        cinemaHallMedium.setCapacity(150);
        cinemaHallMedium.setDescription("Medium cinema hall");
        MovieSession firstMovieSession = new MovieSession();
        firstMovieSession.setMovie(fastAndFurious);
        firstMovieSession.setCinemaHall(cinemaHallBig);
        firstMovieSession.setShowTime(LocalDateTime.of(2025, 4, 12, 21, 54));
        MovieSession secondMovieSession = new MovieSession();
        secondMovieSession.setMovie(theDeparted);
        secondMovieSession.setCinemaHall(cinemaHallMedium);
        secondMovieSession.setShowTime(LocalDateTime.of(2025, 4, 11, 21, 55));

        movieService.add(fastAndFurious);
        movieService.add(theDeparted);
        cinemaHallService.add(cinemaHallBig);
        cinemaHallService.add(cinemaHallMedium);
        movieSessionService.add(firstMovieSession);
        movieSessionService.add(secondMovieSession);

        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(cinemaHallService.get(cinemaHallBig.getId()));
        System.out.println(movieSessionService.get(firstMovieSession.getId()));

        movieService.getAll().forEach(System.out::println);
        cinemaHallService.getAll().forEach(System.out::println);
        movieSessionService.findAvailableSessions(theDeparted.getId(),
                LocalDate.of(2025, 04, 12)).forEach(System.out::println);

    }
}
