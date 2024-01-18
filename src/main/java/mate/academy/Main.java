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
        // Create movie
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie terminator = new Movie("Terminator");
        terminator.setDescription("An action film about robots.");
        movieService.add(terminator);

        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        //Create cinema hall
        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);

        CinemaHall cinemaHallBlue = new CinemaHall(100, "Big hall");
        CinemaHall cinemaHallRed = new CinemaHall(60, "Small hall");

        cinemaHallService.add(cinemaHallBlue);
        cinemaHallService.add(cinemaHallRed);
        System.out.println(cinemaHallService.getAll());

        // Create movie session
        MovieSession movieSessionTerminator = new MovieSession();
        movieSessionTerminator.setMovie(terminator);
        movieSessionTerminator.setCinemaHall(cinemaHallBlue);
        movieSessionTerminator.setShowTime(
                LocalDateTime.of(2023, 11, 5, 21, 30));

        MovieSession movieSessionFast = new MovieSession();
        movieSessionFast.setMovie(fastAndFurious);
        movieSessionFast.setCinemaHall(cinemaHallRed);
        movieSessionFast.setShowTime(
                LocalDateTime.of(2023, 10, 22, 20, 15));

        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);

        movieSessionService.add(movieSessionTerminator);
        movieSessionService.add(movieSessionFast);
        System.out.println(movieSessionService.get(1L));
        System.out.println(movieSessionService.findAvailableSessions(1L,
                LocalDate.of(2023, 10, 22)));
    }
}
