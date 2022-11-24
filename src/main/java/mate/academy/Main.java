package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
        Movie terminatorSeven = new Movie("Terminator 7");
        terminatorSeven.setDescription("Film about future");
        Movie crazyNewYear = new Movie("Crazy New Year");
        crazyNewYear.setDescription("Film for all family");

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        movieService.add(terminatorSeven);
        movieService.add(crazyNewYear);
        CinemaHall greenHall = new CinemaHall(200, "green hall");
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        cinemaHallService.add(greenHall);
        CinemaHall blueHall = new CinemaHall(150, "blue hall");
        cinemaHallService.add(blueHall);
        LocalDateTime todaysLocalDateTime = LocalDateTime.now();
        MovieSession fastAndFuriousSessionToday = new MovieSession(greenHall,
                fastAndFurious, todaysLocalDateTime);
        LocalDateTime yesterdaysLocalDateTime = LocalDateTime.now().minusDays(1L);
        MovieSession terminatorSevenSessionYesterday = new MovieSession(blueHall,
                terminatorSeven, yesterdaysLocalDateTime);
        LocalDateTime thirtyOneDecember = LocalDateTime.of(2022, 12, 31, 12, 00);
        MovieSession newYearSession = new MovieSession(greenHall, crazyNewYear, thirtyOneDecember);
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(fastAndFuriousSessionToday);
        movieSessionService.add(terminatorSevenSessionYesterday);
        movieSessionService.add(newYearSession);

        LocalDate todayLocalDate = LocalDate.from(todaysLocalDateTime);
        LocalDate yesterdayLocalDate = LocalDate.from(yesterdaysLocalDateTime);
        LocalDate newYearsDate = LocalDate.from(thirtyOneDecember);

        List<MovieSession> todaySessionsFilmByIdOne = movieSessionService
                .findAvailableSessions(1L, todayLocalDate);
        System.out.println(todaySessionsFilmByIdOne);
        List<MovieSession> yesterdaySessionFilmByOdTwo = movieSessionService
                .findAvailableSessions(2L, yesterdayLocalDate);
        System.out.println(yesterdaySessionFilmByOdTwo);
        List<MovieSession> newYearsSessionFilmByIdThree = movieSessionService
                .findAvailableSessions(3L, newYearsDate);
        System.out.println(newYearsSessionFilmByIdThree);
    }
}
