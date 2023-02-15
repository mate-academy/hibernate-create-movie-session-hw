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
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall smallHall = new CinemaHall();
        smallHall.setCapacity(97);
        smallHall.setDescription("Small cinema hall");
        cinemaHallService.add(smallHall);
        CinemaHall bigHall = new CinemaHall();
        bigHall.setCapacity(250);
        bigHall.setDescription("Big cinema hall");
        cinemaHallService.add(bigHall);
        cinemaHallService.getAll().forEach(System.out::println);
        //-----------------------------------------------------
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        MovieSession first = new MovieSession(fastAndFurious, smallHall, today);
        MovieSession second = new MovieSession(fastAndFurious, bigHall, tomorrow);
        movieSessionService.add(first);
        movieSessionService.add(second);
        System.out.println(movieSessionService.get(second.getId()));
        System.out.println(movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.now()));
    }
}
