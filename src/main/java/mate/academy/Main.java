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
        final MovieSessionDaoImpl movieSessionDao = new MovieSessionDaoImpl();

        final CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(100);
        cinemaHall.setDescription("big hall");
        final CinemaHall persistedCinemaHall = movieSessionDao.addCinemaHall(cinemaHall);

        final Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        final Movie persistedMovie = movieSessionDao.addMovie(fastAndFurious);

        final MovieSessionService movieSessionService = new
                MovieSessionServiceImpl(movieSessionDao);

        MovieSession movieSession = new MovieSession();
        movieSession.setCinemaHall(persistedCinemaHall);
        movieSession.setMovie(persistedMovie);
        movieSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(movieSession);

        List<MovieSession> availableSessions = movieSessionService
                .findAvailableSessions(persistedMovie.getId(), LocalDate.now());
        availableSessions.forEach(
                x -> System.out.println("\n***Now we have available sessions with movie: '"
                        + x.getMovie().getTitle()
                        + "' at "
                        + x.getShowTime() + "***")
        );
    }
}
