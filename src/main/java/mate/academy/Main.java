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
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        final LocalDateTime dateTimeFirstSession = LocalDateTime
                .of(2024, Month.AUGUST, 22, 15, 0);
        final LocalDateTime dateTimeSecondSession = LocalDateTime
                .of(2024, Month.AUGUST, 22, 20, 30);
        final MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        final CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall retroville = new CinemaHall();
        retroville.setCapacity(50);
        retroville.setDescription("Standard halls with seats of increased comfort in the back "
                + "rows and innovative IMAX and ScreenX formats.");
        cinemaHallService.add(retroville);
        System.out.println(cinemaHallService.get(retroville.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession firstMovieSession = new MovieSession();
        firstMovieSession.setCinemaHall(retroville);
        firstMovieSession.setMovie(fastAndFurious);
        firstMovieSession.setShowTime(dateTimeFirstSession);
        movieSessionService.add(firstMovieSession);
        System.out.println(movieSessionService.get(firstMovieSession.getId()));

        MovieSession movieSession2 = new MovieSession();
        movieSession2.setCinemaHall(retroville);
        movieSession2.setMovie(fastAndFurious);
        movieSession2.setShowTime(dateTimeSecondSession);
        movieSessionService.add(movieSession2);
        System.out.println(movieSessionService.get(movieSession2.getId()));

        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2024, Month.AUGUST, 22)).forEach(System.out::println);
    }
}
