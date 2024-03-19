package mate.academy;

import java.time.LocalDateTime;
import mate.academy.dao.impl.CinemaHallDaoImpl;
import mate.academy.dao.impl.MovieDaoImpl;
import mate.academy.dao.impl.MovieSessionDaoImpl;
import mate.academy.lib.Inject;
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
        @Inject
        MovieService movieService = new MovieServiceImpl(new MovieDaoImpl());

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        Movie titanic = new Movie("Titanic");
        titanic.setDescription("A drama film about cruise liner, love, and tragedy.");
        movieService.add(titanic);
        System.out.println(movieService.get(titanic.getId()));
        movieService.getAll().forEach(System.out::println);

        @Inject
        CinemaHallService cinemaHallService = new CinemaHallServiceImpl(new CinemaHallDaoImpl());

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(70);
        cinemaHall.setDescription("Medium cinema hall");
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setShowTime(LocalDateTime.of(2024, 6, 12, 18, 15));

        @Inject
        MovieSessionService movieSessionService =
                new MovieSessionServiceImpl(new MovieSessionDaoImpl());
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(movieSession.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                movieSession.getShowTime().toLocalDate()).forEach(System.out::println);
    }
}
