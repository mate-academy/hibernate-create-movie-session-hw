package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import mate.academy.dao.impl.MovieSessionDaoImpl;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.MovieSessionService;
import mate.academy.service.impl.MovieSessionServiceImpl;

public class Main {
    public static void main(String[] args) {
        final MovieSessionService movieSessionService = new MovieSessionServiceImpl(
                new MovieSessionDaoImpl());

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(100);
        cinemaHall.setDescription("big hall");

        MovieSession movieSession = new MovieSession();
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setMovie(fastAndFurious);
        movieSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(movieSession);

        List<MovieSession> availableSessions = movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDate.now());
        availableSessions.forEach(
                x -> System.out.println("\n***Now we have available sessions with movie: '"
                        + x.getMovie().getTitle()
                        + "' at "
                        + x.getShowTime() + "***")
        );
    }
}
