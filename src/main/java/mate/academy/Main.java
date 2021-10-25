package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        Movie psycho = new Movie("Psycho");
        psycho.setDescription("Psychological horror thriller film.");
        movieService.add(psycho);
        System.out.println(movieService.get(psycho.getId()));
        Movie knightsTale = new Movie("A Knight's Tale");
        knightsTale.setDescription("Quasi-medieval adventure comedy-romance film.");
        movieService.add(knightsTale);
        System.out.println(movieService.get(knightsTale.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall smallCinemaHall = new CinemaHall(10);
        smallCinemaHall.setDescription("Small cinema hall with comfortable beds");
        cinemaHallService.add(smallCinemaHall);
        System.out.println(cinemaHallService.get(smallCinemaHall.getId()));
        CinemaHall middleCinemaHall = new CinemaHall(30);
        middleCinemaHall.setDescription("Middle cinema hall with comfortable seats");
        cinemaHallService.add(middleCinemaHall);
        System.out.println(cinemaHallService.get(middleCinemaHall.getId()));
        CinemaHall largeCinemaHall = new CinemaHall(60);
        largeCinemaHall.setDescription("Big cinema hall with the cheapest tickets");
        cinemaHallService.add(largeCinemaHall);
        System.out.println(cinemaHallService.get(largeCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        MovieSession movieSession1 = new MovieSession(psycho, smallCinemaHall);
        movieSession1.setShowTime(LocalDateTime.of(2021, 10, 11, 0, 10));
        movieSessionService.add(movieSession1);
        System.out.println(movieSessionService.get(movieSession1.getId()));
        MovieSession movieSession2 = new MovieSession(knightsTale, largeCinemaHall);
        movieSession2.setShowTime(LocalDateTime.of(2021, 10, 10, 19, 25));
        movieSessionService.add(movieSession2);
        System.out.println(movieSessionService.get(movieSession2.getId()));
        MovieSession movieSession3 = new MovieSession(fastAndFurious, middleCinemaHall);
        movieSession3.setShowTime(LocalDateTime.of(2021, 10, 10, 21, 30));
        movieSessionService.add(movieSession3);
        System.out.println(movieSessionService.get(movieSession3.getId()));
        MovieSession movieSession4 = new MovieSession(knightsTale, largeCinemaHall);
        movieSession4.setShowTime(LocalDateTime.of(2021, 10, 11, 16, 45));
        movieSessionService.add(movieSession4);
        System.out.println(movieSessionService.get(movieSession4.getId()));
        movieSessionService.findAvailableSessions(3L,
                LocalDate.of(2021, 10, 10)).forEach(System.out::println);
    }
}
