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
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);

        fulfillMoviesTable(movieService);
        fulfillCinemaHallsTable(cinemaHallService);

        Movie fastAndFurious = movieService.get(1L);
        CinemaHall redHall = cinemaHallService.get(1L);
        MovieSession movieSessionYesterday = new MovieSession();
        movieSessionYesterday.setMovie(fastAndFurious);
        movieSessionYesterday.setCinemaHall(redHall);
        movieSessionYesterday.setShowTime(LocalDateTime.now().minusDays(1));

        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSessionYesterday);

        Movie spiderMan = movieService.get(2L);
        CinemaHall greenHall = cinemaHallService.get(2L);
        MovieSession movieSessionTodayFirst = new MovieSession();
        movieSessionTodayFirst.setMovie(spiderMan);
        movieSessionTodayFirst.setCinemaHall(greenHall);
        movieSessionTodayFirst.setShowTime(LocalDateTime.now());
        movieSessionService.add(movieSessionTodayFirst);

        Movie terminator = movieService.get(3L);
        CinemaHall orangeHall = cinemaHallService.get(3L);
        MovieSession movieSessionTomorrow = new MovieSession();
        movieSessionTomorrow.setMovie(terminator);
        movieSessionTomorrow.setCinemaHall(orangeHall);
        movieSessionTomorrow.setShowTime(LocalDateTime.now().plusDays(1));
        movieSessionService.add(movieSessionTomorrow);

        MovieSession movieSessionTodaySecond = new MovieSession();
        movieSessionTodaySecond.setMovie(terminator);
        movieSessionTodaySecond.setCinemaHall(greenHall);
        movieSessionTodaySecond.setShowTime(LocalDateTime.now().plusHours(1));

        MovieSession movieSessionTodayThird = new MovieSession();
        movieSessionTodayThird.setMovie(fastAndFurious);
        movieSessionTodayThird.setCinemaHall(greenHall);
        movieSessionTodayThird.setShowTime(LocalDateTime.now().plusHours(2));

        movieSessionService.add(movieSessionTodaySecond);
        movieSessionService.add(movieSessionTodayThird);

        System.out.println(movieService.getAll());
        System.out.println(cinemaHallService.getAll());
        System.out.println(movieSessionService.findAvailableSessions(1L, LocalDate.now()));
    }

    private static void fulfillMoviesTable(MovieService movieService) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");

        Movie spiderMan = new Movie("Spider-Man");
        spiderMan.setDescription("An action film about Spider-Man.");

        Movie terminator = new Movie("Terminator");
        terminator.setDescription("An action film about Terminator.");

        movieService.add(fastAndFurious);
        movieService.add(spiderMan);
        movieService.add(terminator);
    }

    private static void fulfillCinemaHallsTable(CinemaHallService cinemaHallService) {
        CinemaHall cinemaHallRed = new CinemaHall();
        cinemaHallRed.setCapacity(500);
        cinemaHallRed.setDescription("Hall in the red color palette");

        CinemaHall cinemaHallGreen = new CinemaHall();
        cinemaHallGreen.setCapacity(700);
        cinemaHallGreen.setDescription("Hall in the green color palette");

        CinemaHall cinemaHallOrange = new CinemaHall();
        cinemaHallOrange.setCapacity(900);
        cinemaHallOrange.setDescription("Hall in the orange color palette");

        cinemaHallService.add(cinemaHallRed);
        cinemaHallService.add(cinemaHallGreen);
        cinemaHallService.add(cinemaHallOrange);

    }
}
