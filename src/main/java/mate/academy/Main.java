package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import mate.academy.dao.CinemaHallDao;
import mate.academy.dao.MovieSessionDao;
import mate.academy.dao.impl.CinemaHallDaoImpl;
import mate.academy.dao.impl.MovieSessionDaoImpl;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.MovieService;
import mate.academy.service.impl.MovieServiceImpl;

public class Main {
    private static final String PATH = "mate.academy";
    private static final Injector injector = Injector.getInstance(PATH);

    public static void main(String[] args) {
        MovieService movieService = (MovieServiceImpl) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        CinemaHallDao cinemaHallService = (CinemaHallDaoImpl) injector
                .getInstance(CinemaHallDao.class);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(10);
        cinemaHall.setDescription("4d hall");
        cinemaHallService.add(cinemaHall);

        LocalDateTime showDateTime = LocalDate.now().atTime(23, 50);
        MovieSession movieSession1 = new MovieSession();
        movieSession1.setMovie(fastAndFurious);
        movieSession1.setCinemaHall(cinemaHall);
        movieSession1.setShowTime(showDateTime);
        MovieSessionDao movieSessionService = (MovieSessionDaoImpl) injector
                .getInstance(MovieSessionDao.class);
        movieSessionService.add(movieSession1);

        System.out.println(movieSessionService.get(movieSession1.getId()));

        LocalDate chosenDate = LocalDate.of(2024, 3, 12);

        List<MovieSession> availableSessions = movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), chosenDate);

        for (MovieSession session : availableSessions) {
            System.out.println(session);
        }

        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
    }
}
