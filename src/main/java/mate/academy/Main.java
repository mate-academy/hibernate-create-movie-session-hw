package mate.academy;

import java.time.LocalDate;
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
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall fastAndFuriousCinemaHall = new CinemaHall();
        fastAndFuriousCinemaHall.setDescription("Cinema hall for movie Fast and Furious");
        fastAndFuriousCinemaHall.setCapacity(250);
        cinemaHallService.add(fastAndFuriousCinemaHall);
        System.out.println(cinemaHallService.get(fastAndFuriousCinemaHall.getId()));
        System.out.println(cinemaHallService.getAll());

        MovieSession fastAndFuriousMovieSession = new MovieSession();
        fastAndFuriousMovieSession.setMovie(fastAndFurious);
        fastAndFuriousMovieSession.setCinemaHall(fastAndFuriousCinemaHall);
        fastAndFuriousMovieSession.setLocalDate(LocalDate.of(2020, 12, 3));
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(fastAndFuriousMovieSession);
        System.out.println(movieSessionService.get(fastAndFuriousMovieSession.getId()));
        movieSessionService.findAvailableSessions(fastAndFuriousMovieSession.getId(),
                LocalDate.of(2020, 12, 3));
    }
}
