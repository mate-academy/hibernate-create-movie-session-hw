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
    private static final Injector injector =
            Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService
                = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie harriPotter = new Movie("Harry Potter and the Deathly Hallows");
        harriPotter.setDescription("It is a fantasy novel written by British author J. K. "
                + "Rowling and the seventh and final novel in the Harry Potter series");
        movieService.add(harriPotter);

        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(movieService.get(harriPotter.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall firstCinemaHall = new CinemaHall();
        firstCinemaHall.setCapacity(100);
        firstCinemaHall.setDescription("5D cinema hall with best sound");
        cinemaHallService.add(firstCinemaHall);

        CinemaHall secondCinemaHall = new CinemaHall();
        secondCinemaHall.setCapacity(150);
        secondCinemaHall.setDescription("3D cinema hall");
        cinemaHallService.add(secondCinemaHall);

        System.out.println("_______________________________________");
        System.out.println(cinemaHallService.get(firstCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println("_______________________________________");

        MovieSession firstMovieSession = new MovieSession();
        firstMovieSession.setMovie(fastAndFurious);
        firstMovieSession.setCinemaHall(firstCinemaHall);
        firstMovieSession.setLocalDateTime(LocalDateTime.of(2024, 5, 11, 10, 30, 0));

        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(firstMovieSession);

        MovieSession secondMovieSession = new MovieSession();
        secondMovieSession.setMovie(fastAndFurious);
        secondMovieSession.setCinemaHall(secondCinemaHall);
        secondMovieSession.setLocalDateTime(LocalDateTime.of(2024, 5, 11, 15, 30, 0));
        movieSessionService.add(secondMovieSession);

        MovieSession thirdMovieSession = new MovieSession();
        thirdMovieSession.setMovie(harriPotter);
        thirdMovieSession.setCinemaHall(firstCinemaHall);
        thirdMovieSession.setLocalDateTime(LocalDateTime.of(2024, 5, 11, 20, 30, 0));
        movieSessionService.add(thirdMovieSession);

        System.out.println("_______________________________________");
        System.out.println(movieSessionService.get(1L));
        System.out.println(movieSessionService
                .findAvailableSessions(1L, LocalDate.of(2024, 5, 11)));
        System.out.println("_______________________________________");
    }
}
