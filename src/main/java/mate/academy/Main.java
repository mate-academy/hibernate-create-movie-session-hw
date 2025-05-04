package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        MovieService movieService = new MovieServiceImpl();
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall spiderMan = new CinemaHall();
        spiderMan.setCapacity(90);
        spiderMan.setDescription("Spider Man");
        CinemaHallService cinemaHallService = new CinemaHallServiceImpl();
        cinemaHallService.add(spiderMan);

        System.out.println(cinemaHallService.get(1L));

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHallService.get(1L));
        movieSession.setShowTime(LocalDateTime.of(
                2005, 12, 18, 14, 30));
        MovieSessionService movieSessionService = new MovieSessionServiceImpl();
        movieSessionService.add(movieSession);

        System.out.println(movieSessionService.findAvailableSessions(
                1L, LocalDate.of(2005, 12, 18)));
    }
}
