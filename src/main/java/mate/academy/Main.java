package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie titanic = new Movie("Titanic");
        titanic.setDescription("Amazing movie");
        movieService.add(fastAndFurious);
        movieService.add(titanic);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHallFirst = new CinemaHall();
        cinemaHallFirst.setCapacity(60);
        cinemaHallFirst.setDescription("First cinema hall");
        cinemaHallService.add(cinemaHallFirst);
        CinemaHall cinemaHallSecond = new CinemaHall();
        cinemaHallSecond.setCapacity(90);
        cinemaHallSecond.setDescription("Second cinema hall");
        cinemaHallService.add(cinemaHallSecond);
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHallSecond);
        LocalDateTime localDateTime = LocalDateTime.of(2023, 7, 11, 23,59);
        movieSession.setLocalDateTime(localDateTime);
        MovieSession movieSession1 = new MovieSession();
        movieSession1.setMovie(fastAndFurious);
        movieSession1.setCinemaHall(cinemaHallFirst);
        LocalDateTime localDateTime1 = LocalDateTime.of(2023, 7, 11, 00,00);
        movieSession1.setLocalDateTime(localDateTime1);
        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(titanic);
        movieSession2.setCinemaHall(cinemaHallFirst);
        LocalDateTime localDateTime2 = LocalDateTime.of(2023, 7, 11, 10,00);
        movieSession2.setLocalDateTime(localDateTime2);

        movieSessionService.add(movieSession);
        movieSessionService.add(movieSession1);
        movieSessionService.add(movieSession2);
        System.out.println(movieSessionService.get(1L));
        System.out.println(movieSessionService.get(2L));
        System.out.println(movieSessionService.get(3L));
        LocalDate localDate = LocalDate.of(2023, 7, 11);
        List<MovieSession> movieSessionList
                = movieSessionService.findAvailableSessions(1L, localDate);
        movieSessionList.forEach(System.out::println);
    }
}
