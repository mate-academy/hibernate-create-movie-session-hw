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
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) INJECTOR.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        System.out.println("------");

        CinemaHallService cinemaHallService =
                (CinemaHallService) INJECTOR.getInstance(CinemaHallService.class);

        CinemaHall bigHall = new CinemaHall();
        bigHall.setCapacity(250);
        bigHall.setDescription("Big hall");
        cinemaHallService.add(bigHall);
        System.out.println(cinemaHallService.get(bigHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println("------");

        MovieSession session = new MovieSession();
        session.setMovie(fastAndFurious);
        session.setCinemaHall(bigHall);
        session.setShowTime(LocalDateTime.of(2023, 4, 7, 12, 30));

        MovieSessionService movieSessionService =
                (MovieSessionService) INJECTOR.getInstance(MovieSessionService.class);

        movieSessionService.add(session);
        System.out.println(movieSessionService.get(session.getId()));
        System.out.println(movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2023, 4, 7)));
        System.out.println(movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2023, 3, 7)));
    }
}
