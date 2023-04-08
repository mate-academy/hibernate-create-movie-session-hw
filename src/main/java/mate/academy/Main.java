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
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        MovieService movieService = (MovieService)injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        System.out.println("-----------------");
        CinemaHall firstHall = new CinemaHall();
        firstHall.setCapacity(100);
        firstHall.setDescription("Is a big hall");
        CinemaHallService cinemaHallService =
                (CinemaHallService)injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(firstHall);
        System.out.println(cinemaHallService.get(firstHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println("-----------------");
        MovieSession session = new MovieSession();
        session.setMovie(fastAndFurious);
        session.setCinemaHall(firstHall);
        session.setShowTime(LocalDateTime.now());
        MovieSessionService movieSessionService =
                (MovieSessionService)injector.getInstance(MovieSessionService.class);
        movieSessionService.add(session);
        System.out.println(movieSessionService.get(session.getId()));
        LocalDate toDay = LocalDate.now();
        movieSessionService.findAvailableSessions(session.getId(), toDay)
                .forEach(System.out::println);
        System.out.println("Empty list:");
        movieSessionService.findAvailableSessions(session.getId(),
                toDay.plusDays(5)).forEach(System.out::println);
    }
}
