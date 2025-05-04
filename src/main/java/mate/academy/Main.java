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
import mate.academy.service.impl.CinemaHallServiceImpl;
import mate.academy.service.impl.MovieServiceImpl;
import mate.academy.service.impl.MovieSessionServiceImpl;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) INJECTOR.getInstance(MovieServiceImpl.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService
                = (CinemaHallService) INJECTOR.getInstance(CinemaHallServiceImpl.class);

        CinemaHall hall = new CinemaHall();
        hall.setCapacity(300);
        hall.setDescription("King size hall)");
        cinemaHallService.add(hall);
        System.out.println(cinemaHallService.get(hall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession session = new MovieSession();
        session.setCinemaHall(hall);
        session.setMovie(fastAndFurious);
        session.setShowTime(LocalDateTime.of(2023, 12, 7, 12, 0));

        MovieSession session1 = new MovieSession();
        session1.setShowTime(LocalDateTime.of(2023, 12,7, 16,0));
        session1.setMovie(fastAndFurious);
        session1.setCinemaHall(hall);

        MovieSessionService movieSessionService
                = (MovieSessionService) INJECTOR.getInstance(MovieSessionServiceImpl.class);
        movieSessionService.add(session);
        movieSessionService.add(session1);
        System.out.println(movieSessionService.get(session.getId()));
        List<MovieSession> availableSessions =
                movieSessionService.findAvailableSessions(1L,
                        LocalDate.of(2023, 12, 7));
        System.out.println("available sessions " + availableSessions);

        List<MovieSession> exceptionSession = movieSessionService
                .findAvailableSessions(2L, LocalDate.of(2023,12,7));
        System.out.println(exceptionSession);
    }
}
