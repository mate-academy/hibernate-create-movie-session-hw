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
        MovieService movieService = new MovieServiceImpl();

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = new CinemaHallServiceImpl();

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(12);
        cinemaHall.setDescription("description1");

        cinemaHallService.add(cinemaHall);
        cinemaHallService.get(1L);
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setShowTime(LocalDateTime.parse("2024-02-06T15:30:45"));

        MovieSessionService movieSessionService = new MovieSessionServiceImpl();
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(1L));

        movieSessionService
                .findAvailableSessions(1L, LocalDate.parse("2024-02-06"))
                .forEach(System.out::println);

    }
}
