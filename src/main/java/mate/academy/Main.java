package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import mate.academy.dao.MovieSessionDao;
import mate.academy.dao.impl.MovieSessionDaoImpl;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;
import mate.academy.service.impl.MovieServiceImpl;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        CinemaHallService cinemaHallService = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        MovieSessionService movieSessionService = (MovieSessionService) injector.getInstance(MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);


        CinemaHall mediumCinemaHall = new CinemaHall(15);
        mediumCinemaHall.setDescription("Medium hall");
        cinemaHallService.add(mediumCinemaHall);

        CinemaHall bigCinemaHall = new CinemaHall(30);
        bigCinemaHall.setDescription("Big hall");
        cinemaHallService.add(bigCinemaHall);

        MovieSession movieSession1 = new MovieSession(LocalDateTime.of(2022, 11, 19,13,1));
        movieSession1.setMovie(fastAndFurious);
        movieSession1.setCinemaHall(mediumCinemaHall);
        movieSessionService.add(movieSession1);

        MovieSession movieSession2 = new MovieSession(LocalDateTime.of(2022, 11, 20,13,1));
        movieSession2.setMovie(fastAndFurious);
        movieSession2.setCinemaHall(bigCinemaHall);
        movieSessionService.add(movieSession2);


        List<MovieSession> availableSessions = movieSessionService.findAvailableSessions(1L, LocalDate.of(2022, 11, 19));
        System.out.println("--------------------");
        availableSessions.forEach(System.out::println);

    }
}
