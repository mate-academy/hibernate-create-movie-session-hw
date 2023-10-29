package mate.academy;

import java.time.LocalDate;
import java.util.List;
import mate.academy.dao.CinemaHallDao;
import mate.academy.dao.MovieDao;
import mate.academy.dao.impl.CinemaHallDaoImpl;
import mate.academy.dao.impl.MovieDaoImpl;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;
import mate.academy.service.impl.MovieServiceImpl;
import mate.academy.service.impl.MovieSessionServiceImpl;

public class Main {
    public static void main(String[] args) {

        MovieDao movieDao = new MovieDaoImpl();
        MovieService movieService = new MovieServiceImpl(movieDao);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        MovieSessionService movieSessionService = new MovieSessionServiceImpl();

        List<MovieSession> availableSessions = movieSessionService
                .findAvailableSessions(1L, LocalDate.now());
        System.out.println(availableSessions);
        CinemaHallDao cinemaHallDao = new CinemaHallDaoImpl();
        cinemaHallDao.add(new CinemaHall());
        System.out.println(cinemaHallDao.get(1L));

    }
}
