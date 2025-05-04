package mate.academy;

import java.time.LocalDate;
import java.util.List;
import mate.academy.lib.Injector;
import mate.academy.model.MovieSession;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);

        MovieSession movieSession = new MovieSession();
        movieSessionService.add(movieSession);

        MovieSession retrievedMovieSession = movieSessionService.get(movieSession.getId());
        System.out.println(retrievedMovieSession);

        List<MovieSession> availableSessions = movieSessionService
                .findAvailableSessions(movieSession.getMovie().getId(), LocalDate.now());
        availableSessions.forEach(System.out::println);
    }
}
