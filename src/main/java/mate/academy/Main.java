package mate.academy;

import java.time.LocalDate;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) INJECTOR.getInstance(MovieService.class);
        CinemaHallService cinemaHallService =
                (CinemaHallService) INJECTOR.getInstance(CinemaHallService.class);
        MovieSessionService movieSessionService =
                (MovieSessionService) INJECTOR.getInstance(MovieSessionService.class);

        insertMovies(movieService);
        insertCinemaHalls(cinemaHallService);
        insertSessions(cinemaHallService, movieService, movieSessionService);

        System.out.println(movieService.get(2L));
        movieService.getAll().forEach(System.out::println);
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println(movieSessionService.get(1L));
        System.out.println(movieSessionService.findAvailableSessions(1L, LocalDate.now()));
    }

    private static void insertSessions(CinemaHallService cinemaHallService,
                                       MovieService movieService,
                                       MovieSessionService movieSessionService) {
        MovieSession session1 = new MovieSession();
        session1.setCinemaHall(cinemaHallService.get(1L));
        session1.setMovie(movieService.get(1L));
        session1.setShowTime(LocalDate.now().atTime(20, 0));
        movieSessionService.add(session1);

        MovieSession session2 = new MovieSession();
        session2.setCinemaHall(cinemaHallService.get(1L));
        session2.setMovie(movieService.get(1L));
        session2.setShowTime(LocalDate.now().atTime(22, 0));
        movieSessionService.add(session2);

        MovieSession session3 = new MovieSession();
        session3.setCinemaHall(cinemaHallService.get(2L));
        session3.setMovie(movieService.get(3L));
        session3.setShowTime(LocalDate.now().minusDays(1).atTime(17, 0));
        movieSessionService.add(session3);
    }

    private static void insertCinemaHalls(CinemaHallService cinemaHallService) {
        CinemaHall redCinemaHall = new CinemaHall();
        redCinemaHall.setCapacity(100);
        redCinemaHall.setDescription("Newest and biggest cinema hall");
        cinemaHallService.add(redCinemaHall);

        CinemaHall blueCinemaHall = new CinemaHall();
        blueCinemaHall.setCapacity(70);
        blueCinemaHall.setDescription("Best colling cinema hall");
        cinemaHallService.add(blueCinemaHall);

        CinemaHall yellowCinemaHall = new CinemaHall();
        yellowCinemaHall.setCapacity(15);
        yellowCinemaHall.setDescription("Vip cinema hall");
        cinemaHallService.add(yellowCinemaHall);
    }

    private static void insertMovies(MovieService movieService) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie terminator = new Movie("Terminator 2");
        terminator.setDescription("Best action movie with steel Arnie");
        movieService.add(terminator);

        Movie movie = new Movie("X-Men");
        movie.setDescription("Will x-Men defeat dangerous enemy?");
        movieService.add(movie);
    }
}
