package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import mate.academy.dao.CinemaHallDao;
import mate.academy.dao.MovieDao;
import mate.academy.dao.MovieSessionDao;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.MovieSessionService;
import mate.academy.util.Injector;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        final MovieSessionService movieSessionService = (
                MovieSessionService) injector.getInstance(MovieSessionService.class);
        final CinemaHallDao cinemaHallDao = (
                CinemaHallDao) injector.getInstance(CinemaHallDao.class);
        final MovieDao movieDao = (
                MovieDao) injector.getInstance(MovieDao.class);
        final MovieSessionDao movieSessionDao = (
                MovieSessionDao) injector.getInstance(MovieSessionDao.class);

        final CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(100);
        cinemaHall.setDescription("big hall");
        final CinemaHall persistedCinemaHall = cinemaHallDao.add(cinemaHall);

        final Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        final Movie persistedMovie = movieDao.add(fastAndFurious);

        final MovieSession movieSession = new MovieSession();
        movieSession.setCinemaHall(persistedCinemaHall);
        movieSession.setMovie(persistedMovie);
        movieSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(movieSession);

        final List<MovieSession> availableSessions = movieSessionService
                .findAvailableSessions(persistedMovie.getId(), LocalDate.now());
        availableSessions.forEach(
                session -> System.out.println("\n***Now we have available sessions with movie: '"
                        + session.getMovie().getTitle()
                        + "' at "
                        + session.getShowTime() + "***")
        );
    }
}
