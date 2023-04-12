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
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final MovieService movieService
            = (MovieService) injector.getInstance(MovieService.class);
    private static final MovieSessionService movieSessionService
            = (MovieSessionService) injector.getInstance(MovieSessionService.class);
    private static final CinemaHallService cinemaHallService
            = (CinemaHallService) injector.getInstance(CinemaHallService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall bigHall = new CinemaHall();
        bigHall.setCapacity(150);
        bigHall.setDescription("Big hall for 150 viewers. Enjoy the movie in the best cinema ever");
        cinemaHallService.add(bigHall);
        System.out.println(cinemaHallService.get(bigHall.getId()));
        cinemaHallService.getAll();

        MovieSession firstSession = new MovieSession();
        LocalDate date = LocalDate.of(2023, 4, 12);
        LocalTime time = LocalTime.of(10, 00);
        LocalDateTime localDateTime = LocalDateTime.of(date, time);
        firstSession.setCinemaHall(cinemaHallService.get(1L));
        firstSession.setMovie(movieService.get(1L));
        firstSession.setShowTime(localDateTime);
        movieSessionService.add(firstSession);
        System.out.println(movieSessionService.get(firstSession.getId()));
        System.out.println(movieSessionService.findAvailableSessions(1L, date));
    }
}
