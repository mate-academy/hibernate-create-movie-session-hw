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
        final MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        Movie kingKong = new Movie("King Kong");
        kingKong.setDescription("An action film about big monkey.");
        movieService.add(kingKong);
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(movieService.get(kingKong.getId()));
        movieService.getAll().forEach(System.out::println);

        final CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
        CinemaHall cinemaHallSmall = new CinemaHall();
        cinemaHallSmall.setCapacity(100);
        cinemaHallSmall.setDescription("Small cinema hall");
        cinemaHallService.add(cinemaHallSmall);
        CinemaHall cinemaHallBig = new CinemaHall();
        cinemaHallBig.setCapacity(500);
        cinemaHallBig.setDescription("Big cinema hall");
        cinemaHallService.add(cinemaHallBig);
        System.out.println(cinemaHallService.get(cinemaHallSmall.getId()));
        System.out.println(cinemaHallService.get(cinemaHallBig.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        final MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        MovieSession movieSession1 = new MovieSession();
        movieSession1.setMovie(kingKong);
        movieSession1.setCinemaHall(cinemaHallSmall);
        LocalDateTime showTime1 = LocalDateTime.of(2022, 9, 13,
                10, 00);
        movieSession1.setShowTime(showTime1);
        movieSessionService.add(movieSession1);
        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(fastAndFurious);
        movieSession2.setCinemaHall(cinemaHallBig);
        LocalDateTime showTime2 = LocalDateTime.of(2022, 10, 27,
                12, 00);
        movieSession2.setShowTime(showTime2);
        movieSessionService.add(movieSession2);
        MovieSession movieSession3 = new MovieSession();
        movieSession3.setMovie(fastAndFurious);
        movieSession3.setCinemaHall(cinemaHallSmall);
        LocalDateTime showTime3 = LocalDateTime.of(2022, 10, 27,
                18, 00);
        movieSession3.setShowTime(showTime3);
        movieSessionService.add(movieSession3);
        System.out.println("--------------- Movie session 1 ---------------");
        System.out.println(movieSessionService.get(movieSession1.getId()));
        System.out.println("--------------- Movie session 2 ---------------");
        System.out.println(movieSessionService.get(movieSession2.getId()));
        System.out.println("--------------- Movie session 3 ---------------");
        System.out.println(movieSessionService.get(movieSession3.getId()));
        System.out.println("--------------- Res 1 ---------------");
        movieSessionService.findAvailableSessions(kingKong.getId(), LocalDate.of(
                2022, 9, 13)).forEach(System.out::println);
        System.out.println("--------------- Res 2 ---------------");
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.of(
                2022, 10, 27)).forEach(System.out::println);
    }
}
