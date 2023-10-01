package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final LocalDate date = LocalDate.of(2023, Month.JULY, 10);
    private static final LocalTime time1 = LocalTime.of(10, 30);
    private static final LocalTime time2 = LocalTime.of(15, 30);
    private static final LocalDateTime localDateTime1 = LocalDateTime.of(date, time1);
    private static final LocalDateTime localDateTime2 = LocalDateTime.of(date, time2);
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
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(50);
        cinemaHall.setDescription("Cinema hall 1");
        cinemaHallService.add(cinemaHall);

        CinemaHall cinemaHallVip = new CinemaHall();
        cinemaHallVip.setCapacity(20);
        cinemaHallVip.setDescription("Cinema hall VIP");
        cinemaHallService.add(cinemaHallVip);

        MovieSession movieSession1 = new MovieSession();
        movieSession1.setMovie(fastAndFurious);
        movieSession1.setCinemaHall(cinemaHall);
        movieSession1.setShowTime(localDateTime1);
        movieSessionService.add(movieSession1);

        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(fastAndFurious);
        movieSession2.setCinemaHall(cinemaHallVip);
        movieSession2.setShowTime(localDateTime2);
        movieSessionService.add(movieSession2);

        movieSessionService.findAvailableSessions(1L, date).forEach(System.out::println);
    }
}
