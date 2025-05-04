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

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final MovieService movieService
            = (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService
            = (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService
            = (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie harryPotter = new Movie("Harry Potter and the chamber of secrets");
        harryPotter.setDescription("The plot follows Harry's second year at Hogwarts, during "
                + "which a series of messages on the walls of the school's corridors warn that the"
                + " \"Chamber of Secrets\" has been opened and that the \"heir of Slytherin\" "
                + "would kill all pupils who do not come from all-magical families.");
        movieService.add(harryPotter);
        System.out.println(movieService.get(harryPotter.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall liniaKino = new CinemaHall();
        liniaKino.setCapacity(1000);
        liniaKino.setDescription("Magelan cinema hall");
        cinemaHallService.add(liniaKino);
        System.out.println(cinemaHallService.get(liniaKino.getId()));
        System.out.println(cinemaHallService.getAll());

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(harryPotter);
        movieSession.setCinemaHall(liniaKino);
        movieSession.setShowTime(LocalDateTime.of(2021, 7, 6, 15, 0));
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(movieSession.getId()));

        List<MovieSession> availableSessions
                = movieSessionService.findAvailableSessions(harryPotter.getId(),
                LocalDate.from(LocalDateTime.now()));
        System.out.println(availableSessions);
    }
}
