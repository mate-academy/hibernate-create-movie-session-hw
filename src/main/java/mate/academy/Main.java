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
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);

        Movie beautyAndTheBeast = new Movie("Beauty and the Beast");
        beautyAndTheBeast.setDescription("A musical romantic fantasy film.");
        movieService.add(beautyAndTheBeast);

        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(movieService.get(beautyAndTheBeast.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHallRed = new CinemaHall();
        cinemaHallRed.setCapacity(50);
        cinemaHallRed.setDescription("RED");
        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHallRed);

        CinemaHall cinemaHallBlue = new CinemaHall();
        cinemaHallBlue.setCapacity(150);
        cinemaHallBlue.setDescription("BLUE");
        cinemaHallService.add(cinemaHallBlue);

        CinemaHall cinemaHallGreen = new CinemaHall();
        cinemaHallGreen.setCapacity(100);
        cinemaHallGreen.setDescription("GREEN");
        cinemaHallService.add(cinemaHallGreen);

        System.out.println(cinemaHallService.get(cinemaHallRed.getId()));
        System.out.println(cinemaHallService.get(cinemaHallBlue.getId()));
        System.out.println(cinemaHallService.get(cinemaHallGreen.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession fastAndFuriousAtRedSession = new MovieSession();
        fastAndFuriousAtRedSession.setMovie(fastAndFurious);
        fastAndFuriousAtRedSession.setCinemaHall(cinemaHallRed);
        fastAndFuriousAtRedSession.setShowTime(LocalDateTime
                .of(2021, 8, 5, 12, 0));
        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        movieSessionService.add(fastAndFuriousAtRedSession);

        MovieSession fastAndFuriousAtBlueSession = new MovieSession();
        fastAndFuriousAtBlueSession.setMovie(fastAndFurious);
        fastAndFuriousAtBlueSession.setCinemaHall(cinemaHallBlue);
        fastAndFuriousAtBlueSession.setShowTime(LocalDateTime
                .of(2021, 8, 4, 18, 0));
        movieSessionService.add(fastAndFuriousAtBlueSession);

        MovieSession beautyAndTheBeastAtBlueSession = new MovieSession();
        beautyAndTheBeastAtBlueSession.setMovie(beautyAndTheBeast);
        beautyAndTheBeastAtBlueSession.setCinemaHall(cinemaHallBlue);
        beautyAndTheBeastAtBlueSession.setShowTime(LocalDateTime
                .of(2021, 8, 4, 15, 0));
        movieSessionService.add(beautyAndTheBeastAtBlueSession);

        MovieSession beautyAndTheBeastAtGreenSession = new MovieSession();
        beautyAndTheBeastAtGreenSession.setMovie(beautyAndTheBeast);
        beautyAndTheBeastAtGreenSession.setCinemaHall(cinemaHallGreen);
        beautyAndTheBeastAtGreenSession.setShowTime(LocalDateTime
                .of(2021, 8, 4, 20, 0));
        movieSessionService.add(beautyAndTheBeastAtGreenSession);

        System.out.println(movieSessionService.get(fastAndFuriousAtRedSession.getId()));
        System.out.println(movieSessionService.get(fastAndFuriousAtBlueSession.getId()));
        System.out.println(movieSessionService.get(beautyAndTheBeastAtBlueSession.getId()));
        System.out.println(movieSessionService.get(beautyAndTheBeastAtGreenSession.getId()));

        LocalDate date = LocalDate.of(2021, 8, 4);
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), date)
                .forEach(System.out::println);
    }
}
