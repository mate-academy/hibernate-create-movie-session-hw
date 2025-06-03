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
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService hallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        CinemaHall hall = hallService.add(new CinemaHall(100, "iMax Hall"));
        MovieSession session = new MovieSession();
        session.setMovie(fastAndFurious);
        session.setCinemaHall(hall);
        session.setShowTime(LocalDateTime.now().plusDays(1));

        MovieSessionService sessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        sessionService.add(session);

        List<MovieSession> tomorrowSessions = sessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDate.now().plusDays(1));
        tomorrowSessions.forEach(System.out::println);
    }
}
