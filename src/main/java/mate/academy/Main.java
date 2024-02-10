package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import mate.academy.dao.CinemaHallDao;
import mate.academy.dao.MovieDao;
import mate.academy.dao.MovieSessionDao;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;
import mate.academy.service.impl.CinemaHallServiceImpl;
import mate.academy.service.impl.MovieServiceImpl;
import mate.academy.service.impl.MovieSessionServiceImpl;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieDao movieDao = (MovieDao) injector.getInstance(MovieDao.class);
        MovieService movieService = new MovieServiceImpl(movieDao);

        Movie fastAndFurious = new Movie("Fast and Furious",
                "An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie harryPotter = new Movie("Harry Potter", "Interesting movie");
        movieService.add(harryPotter);

        CinemaHallDao cinemaHallDao = (CinemaHallDao) injector.getInstance(CinemaHallDao.class);
        CinemaHallService cinemaHallService = new CinemaHallServiceImpl(cinemaHallDao);

        CinemaHall middleCinemaHall = new CinemaHall(20, "Middle Hall");
        cinemaHallService.add(middleCinemaHall);

        CinemaHall bigCinemaHall = new CinemaHall(100, "Big cinema hall");
        cinemaHallService.add(bigCinemaHall);

        MovieSessionDao movieSessionDao =
                (MovieSessionDao) injector.getInstance(MovieSessionDao.class);
        MovieSessionService movieSessionService = new MovieSessionServiceImpl(movieSessionDao);

        LocalDateTime dateTimeSessionFastAndFurious =
                LocalDateTime.parse("2024-02-09 16:30",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.US));
        MovieSession firstSession = new MovieSession(fastAndFurious, middleCinemaHall,
                dateTimeSessionFastAndFurious);
        movieSessionService.add(firstSession);

        LocalDateTime dateTimeSessionHarryPotter =
                LocalDateTime.parse("2024-02-09 16:30",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.US));
        MovieSession secondSession = new MovieSession(harryPotter, middleCinemaHall,
                dateTimeSessionHarryPotter);
        movieSessionService.add(secondSession);

        LocalDateTime dateTimeSecondSessionHarryPotter =
                LocalDateTime.parse("2024-02-09 18:30",
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK));
        MovieSession thirdSession = new MovieSession(harryPotter, bigCinemaHall,
                dateTimeSecondSessionHarryPotter);
        movieSessionService.add(thirdSession);

        System.out.println(movieSessionService.findAvailableSessions(2L,
                LocalDate.of(2024, 2, 9)));
    }
}
