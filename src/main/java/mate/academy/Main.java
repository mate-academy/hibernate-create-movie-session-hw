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
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);

    public static void main(String[] args) {
        System.out.println("Create movies: ");
        Movie fastAndFurious = new Movie();
        fastAndFurious.setTitle("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie terminator = new Movie();
        terminator.setTitle("Terminator");
        terminator.setDescription("I'll be back!");

        Movie firstMovieInDB = movieService.add(fastAndFurious);
        Movie secondMovieInDB = movieService.add(terminator);
        System.out.println("Create movies in DB: ");
        System.out.println(firstMovieInDB);
        System.out.println(secondMovieInDB);

        System.out.println("Get movies from db by id");
        System.out.println(movieService.get(firstMovieInDB.getId()));
        System.out.println(movieService.get(secondMovieInDB.getId()));

        System.out.println("Get all movies from db:");
        movieService.getAll().forEach(System.out::println);

        System.out.println();
        System.out.println("Create cinemahals");
        CinemaHall fistHall = new CinemaHall();
        fistHall.setCapacity(100);
        fistHall.setDescription("3D hall");
        CinemaHall secondHall = new CinemaHall();
        secondHall.setCapacity(200);
        secondHall.setDescription("IMAX hall");

        CinemaHall firstHallInDB = cinemaHallService.add(fistHall);
        CinemaHall secondHallInDB = cinemaHallService.add(secondHall);
        System.out.println("Create cinemahals in db:");
        System.out.println(firstHallInDB);
        System.out.println(secondHallInDB);

        System.out.printf("Get cinemahalls by id");
        System.out.println(cinemaHallService.get(firstHallInDB.getId()));
        System.out.println(cinemaHallService.get(secondHallInDB.getId()));
        System.out.println("Get all cinemahalls");
        cinemaHallService.getAll().forEach(System.out::println);

        System.out.println();
        System.out.println("creating moviesessions");
        MovieSession firstMovieSession = new MovieSession();
        firstMovieSession.setMovie(firstMovieInDB);
        firstMovieSession.setCinemaHall(firstHallInDB);
        firstMovieSession.setShowTime(LocalDateTime.of(2023, 4, 12,
                23, 00, 00));

        MovieSession secondMovieSession = new MovieSession();
        secondMovieSession.setCinemaHall(secondHallInDB);
        secondMovieSession.setMovie(secondMovieInDB);
        secondMovieSession.setShowTime(LocalDateTime.of(2023, 4, 12,
                22, 00, 00));

        MovieSession firstMovieSessionInDB = movieSessionService.add(firstMovieSession);
        MovieSession secondMovieSessionInDB = movieSessionService.add(secondMovieSession);
        System.out.println("Create moviessessions in db:");
        System.out.println(firstMovieSessionInDB);
        System.out.println(secondMovieSessionInDB);
        System.out.println(movieSessionService.get(firstMovieSessionInDB.getId()));
        System.out.println(movieSessionService.get(secondMovieSessionInDB.getId()));
        List<MovieSession> availableSessions =
                movieSessionService.findAvailableSessions(firstMovieInDB.getId(),
                        LocalDate.of(2023, 4, 12));
        System.out.println("Get available sessions");
        System.out.println(availableSessions);
    }
}
