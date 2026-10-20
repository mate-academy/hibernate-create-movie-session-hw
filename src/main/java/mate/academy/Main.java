package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        final MovieSessionService sessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious",
                "An action film about street racing, heists, and spies.");
        CinemaHall hall1 = new CinemaHall(12, "Hall 1, 3D is available");

        MovieSession session = new MovieSession();

        session.setMovie(fastAndFurious);
        session.setCinemaHall(hall1);
        session.setShowTime(LocalDateTime.now());

        sessionService.add(session);

        System.out.println("sessionService.get(session) = "
                + sessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.now()));
    }
}
