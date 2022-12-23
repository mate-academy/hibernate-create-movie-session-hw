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

        CinemaHall imaxHall = new CinemaHall(50);
        imaxHall.setDescription("IMAX is a system of high-resolution cameras, "
                + "film formats, film projectors, and theaters known for having "
                + "very large screens with a tall aspect ratio");
        cinemaHallService.add(imaxHall);
        System.out.println(cinemaHallService.get(imaxHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession fastAndFuriousSession = new MovieSession(fastAndFurious);
        fastAndFuriousSession.setCinemaHall(imaxHall);
        fastAndFuriousSession.setShowTime(LocalDateTime.of(LocalDate.of(2022,12,5),
                LocalTime.of(17, 0)));
        movieSessionService.add(fastAndFuriousSession);
        System.out.println(movieSessionService.get(fastAndFuriousSession.getId()));
        movieSessionService.findAvailableSessions(fastAndFuriousSession.getId(),
                LocalDate.of(2022,12,5)).forEach(System.out::println);
        movieSessionService.findAvailableSessions(fastAndFuriousSession.getId(),
                LocalDate.now()).forEach(System.out::println);
    }
}
