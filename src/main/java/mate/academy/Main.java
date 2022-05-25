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
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall firstHall = new CinemaHall();
        firstHall.setCapacity(200);
        firstHall.setDescription("1st hall");
        cinemaHallService.add(firstHall);
        System.out.println(cinemaHallService.get(firstHall.getId()));
        CinemaHall secondHall = new CinemaHall();
        secondHall.setCapacity(300);
        secondHall.setDescription("2nd hall");
        cinemaHallService.add(secondHall);
        System.out.println(cinemaHallService.get(secondHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        MovieSession morningSession = new MovieSession(fastAndFurious);
        morningSession.setCinemaHall(firstHall);
        morningSession.setLocalDateTime(
                LocalDateTime.of(2022, 5, 25, 9, 00));
        movieSessionService.add(morningSession);
        System.out.println(movieSessionService.get(morningSession.getId()));
        MovieSession afternoonSession = new MovieSession(fastAndFurious);
        afternoonSession.setCinemaHall(firstHall);
        afternoonSession.setLocalDateTime(
                LocalDateTime.of(2022, 5, 25, 17, 00));
        movieSessionService.add(afternoonSession);
        System.out.println(movieSessionService.get(afternoonSession.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                        LocalDate.of(2022, Month.MAY, 25))
                .forEach(System.out::println);
    }
}
