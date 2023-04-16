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
    private static final Injector injector =
            Injector.getInstance("mate.academy");
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);
    private static final int RED_HALL_CAPACITY = 69;
    private static final String COLOR_ANSI_BEGIN = "\033[32;40m";
    private static final String COLOR_ANSI_END = "\033[0m";

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(COLOR_ANSI_BEGIN + movieService.get(fastAndFurious.getId())
                + COLOR_ANSI_BEGIN);
        movieService.getAll()
                .forEach((m) -> System.out.println(COLOR_ANSI_BEGIN + m + COLOR_ANSI_END));
        System.out.println(System.lineSeparator());

        CinemaHall redHall = new CinemaHall();
        redHall.setCapacity(RED_HALL_CAPACITY);
        redHall.setDescription("Red hall");
        cinemaHallService.add(redHall);
        System.out.println(COLOR_ANSI_BEGIN + cinemaHallService.get(redHall.getId())
                + COLOR_ANSI_END);
        cinemaHallService.getAll()
                .forEach((ch) -> System.out.println(COLOR_ANSI_BEGIN + ch + COLOR_ANSI_END));
        System.out.println(System.lineSeparator());

        MovieSession morningSession = new MovieSession();
        morningSession.setMovie(fastAndFurious);
        morningSession.setCinemaHall(redHall);
        morningSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(morningSession);
        System.out.println(COLOR_ANSI_BEGIN + movieSessionService.get(morningSession.getId())
                + COLOR_ANSI_END);
        System.out.println(System.lineSeparator());
        LocalDate today = LocalDate.now();
        System.out.println(COLOR_ANSI_BEGIN + "Sessions available today:" + COLOR_ANSI_END);
        movieSessionService.findAvailableSessions(morningSession.getId(), today)
                .forEach((s) -> System.out.println(COLOR_ANSI_BEGIN + s + COLOR_ANSI_END));
        System.out.println(System.lineSeparator());
        System.out.println(COLOR_ANSI_BEGIN + "Sessions available in 3 days :" + COLOR_ANSI_END);
        movieSessionService.findAvailableSessions(morningSession.getId(),
                today.plusDays(3
                ))
                .forEach((s) -> System.out.println(COLOR_ANSI_BEGIN + s + COLOR_ANSI_END));
        System.out.println(System.lineSeparator());
    }
}
