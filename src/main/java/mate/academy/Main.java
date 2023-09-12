package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import mate.academy.dao.impl.CinemaHallDaoImpl;
import mate.academy.dao.impl.MovieDaoImpl;
import mate.academy.dao.impl.MovieSessionDaoImpl;
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

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        MovieService movieService = new MovieServiceImpl(new MovieDaoImpl());
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall(100, "BigHall");
        CinemaHallService cinemaHallService = new CinemaHallServiceImpl(new CinemaHallDaoImpl());
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSession
                = new MovieSession(fastAndFurious, cinemaHall, LocalDateTime.now());
        MovieSessionService movieSessionService
                = new MovieSessionServiceImpl(new MovieSessionDaoImpl());
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(movieSession.getId()));
        movieSessionService.findAvailableSessions(movieSession.getId(), LocalDate.now())
                .forEach(System.out::println);
    }
}
