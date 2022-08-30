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
    private static final String INJECTOR_INSTANCE = "mate.academy";
    private static final Injector injector = Injector.getInstance(INJECTOR_INSTANCE);
    private static final LocalDateTime SHOW_TIME =
            LocalDateTime.of(2022, 8, 30, 17, 40);
    private static final LocalDate SESSION_DATE = LocalDate.now();
    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        CinemaHall hallMiramax = new CinemaHall();
        hallMiramax.setCapacity(144);
        hallMiramax.setDescription("3D super vision technology");
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHall.class);
        cinemaHallService.add(hallMiramax);
        MovieSession fastAndFuriousSession = new MovieSession();
        fastAndFuriousSession.setMovie(fastAndFurious);
        fastAndFuriousSession.setCinemaHall(hallMiramax);
        fastAndFuriousSession.setShowTime(SHOW_TIME);
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(fastAndFuriousSession);
        System.out.println(movieSessionService.findAvailableSessions(fastAndFurious.getId(), SESSION_DATE));
    }
}
