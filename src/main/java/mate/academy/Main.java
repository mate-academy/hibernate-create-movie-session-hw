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
    private static final Injector injector = Injector
            .getInstance("mate.academy");
    private static final MovieService movieService = (MovieService) injector
            .getInstance(MovieService.class);
    private static final MovieSessionService movieSessionService = (MovieSessionService) injector
            .getInstance(MovieSessionService.class);
    private static final CinemaHallService cinemaHallService = (CinemaHallService) injector
            .getInstance(CinemaHallService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall red = new CinemaHall();
        red.setCapacity(100);
        red.setDescription("Red");
        cinemaHallService.add(red);
        System.out.println(cinemaHallService.get(red.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession midDay = new MovieSession();
        midDay.setMovie(fastAndFurious);
        midDay.setCinemaHall(red);
        LocalDate localDate = LocalDate.of(2023, 2, 14);
        LocalTime localTime = LocalTime.of(14, 0);
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        midDay.setShowTime(localDateTime);
        movieSessionService.add(midDay);
        System.out.println(movieSessionService.get(midDay.getId()));
        movieSessionService.findAvailableSessions(midDay.getId(), localDate)
                .forEach(System.out::println);
    }
}
