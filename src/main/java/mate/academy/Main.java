package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final String ROW_SEPARATOR = "//////////////////////////////////////////////////"
            + System.lineSeparator();
    private static final LocalDate FIRST_DATE = LocalDate.of(1111, 1, 1);
    private static final LocalDate SECOND_DATE = LocalDate.of(2222, 2, 2);
    private static final LocalDate THIRD_DATE = LocalDate.of(3333, 3, 3);
    private static final LocalTime FIRST_TIME = LocalTime.of(23, 59, 59);
    private static final LocalTime SECOND_TIME = LocalTime.of(12, 12, 12);
    private static final LocalTime THIRD_TIME = LocalTime.of(0, 0, 0);

    public static void main(String[] args) {
        MovieService movieService
                = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about spies.");
        movieService.add(fastAndFurious);
        Movie fastAndFurious2 = new Movie("Fast and Furious2");
        fastAndFurious2.setDescription("An action film about spies.22222");
        movieService.add(fastAndFurious2);
        Movie fastAndFurious3 = new Movie("Fast and Furious3");
        fastAndFurious3.setDescription("An action film about spies.33333");
        movieService.add(fastAndFurious3);

        System.out.println(ROW_SEPARATOR + movieService.getAll() + ROW_SEPARATOR);

        CinemaHall small = new CinemaHall();
        small.setCapacity(333);
        small.setDescription("Small hall");
        CinemaHall medium = new CinemaHall();
        medium.setCapacity(55555);
        medium.setDescription("Medium hall");
        CinemaHall big = new CinemaHall();
        big.setCapacity(7777777);
        big.setDescription("Big hall");

        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(small);
        cinemaHallService.add(medium);
        cinemaHallService.add(big);

        System.out.println(ROW_SEPARATOR + cinemaHallService.get(small.getId()));
        System.out.println(cinemaHallService.get(medium.getId()));
        System.out.println(cinemaHallService.get(big.getId()));
        System.out.println(cinemaHallService.getAll() + ROW_SEPARATOR);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(small);
        movieSession.setShowTime(LocalDateTime.of(FIRST_DATE, SECOND_TIME));

        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(fastAndFurious);
        movieSession2.setCinemaHall(small);
        movieSession2.setShowTime(LocalDateTime.of(SECOND_DATE, FIRST_TIME));

        MovieSession movieSession3 = new MovieSession();
        movieSession3.setMovie(fastAndFurious2);
        movieSession3.setCinemaHall(medium);
        movieSession3.setShowTime(LocalDateTime.of(SECOND_DATE, SECOND_TIME));

        MovieSession movieSession4 = new MovieSession();
        movieSession4.setMovie(fastAndFurious3);
        movieSession4.setCinemaHall(big);
        movieSession4.setShowTime(LocalDateTime.of(SECOND_DATE, THIRD_TIME));

        MovieSession movieSession6 = new MovieSession();
        movieSession6.setMovie(fastAndFurious);
        movieSession6.setCinemaHall(small);
        movieSession6.setShowTime(LocalDateTime.of(SECOND_DATE, THIRD_TIME));

        MovieSession movieSession7 = new MovieSession();
        movieSession7.setMovie(fastAndFurious2);
        movieSession7.setCinemaHall(medium);
        movieSession7.setShowTime(LocalDateTime.of(SECOND_DATE, FIRST_TIME));

        MovieSession movieSession8 = new MovieSession();
        movieSession8.setMovie(fastAndFurious3);
        movieSession8.setCinemaHall(big);
        movieSession8.setShowTime(LocalDateTime.of(SECOND_DATE, SECOND_TIME));

        MovieSession movieSession5 = new MovieSession();
        movieSession5.setMovie(fastAndFurious);
        movieSession5.setCinemaHall(small);
        movieSession5.setShowTime(LocalDateTime.of(THIRD_DATE, SECOND_TIME));

        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        movieSessionService.add(movieSession2);
        movieSessionService.add(movieSession3);
        movieSessionService.add(movieSession4);
        movieSessionService.add(movieSession5);
        movieSessionService.add(movieSession6);
        movieSessionService.add(movieSession7);
        movieSessionService.add(movieSession8);

        System.out.println(ROW_SEPARATOR
                + movieSessionService.findAvailableSessions(1L, FIRST_DATE));
        System.out.println(movieSessionService
                .findAvailableSessions(2L, FIRST_DATE));
        System.out.println(movieSessionService
                .findAvailableSessions(3L, FIRST_DATE) + ROW_SEPARATOR);

        System.out.println(movieSessionService.findAvailableSessions(1L, SECOND_DATE));
        System.out.println(movieSessionService.findAvailableSessions(2L, SECOND_DATE));
        System.out.println(movieSessionService.findAvailableSessions(3L, SECOND_DATE));

        System.out.println(ROW_SEPARATOR
                + movieSessionService.findAvailableSessions(1L, THIRD_DATE));
        System.out.println(movieSessionService
                .findAvailableSessions(2L, THIRD_DATE));
        System.out.println(movieSessionService
                .findAvailableSessions(3L, THIRD_DATE) + ROW_SEPARATOR);
    }
}
