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
    private static final Injector injector =
            Injector.getInstance("mate.academy");
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        Movie piratesOfTheCaribbean = new Movie("Pirates of the Caribbean");
        piratesOfTheCaribbean.setDescription("Is an American fantasy supernatural swashbuckler "
                + "film series produced by Jerry Bruckheimer and based "
                + "on Walt Disney's theme park attraction of the same name.");
        movieService.add(piratesOfTheCaribbean);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall firstCinemaHall = new CinemaHall(80, "some description");
        CinemaHall secondCinemaHall = new CinemaHall(120, "some description");
        cinemaHallService.add(firstCinemaHall);
        cinemaHallService.add(secondCinemaHall);
        System.out.println(cinemaHallService.get(2L));
        cinemaHallService.getAll().forEach(System.out::println);

        LocalDateTime firstMovieSessionDate = LocalDateTime.of(2023, 1, 1, 12,0);
        LocalDateTime secondMovieSessionDate = LocalDateTime.of(2023, 12, 31, 12,0);
        MovieSession firstMovieSession =
                new MovieSession(fastAndFurious, firstCinemaHall, firstMovieSessionDate);
        MovieSession secondMovieSession =
                new MovieSession(fastAndFurious, secondCinemaHall, secondMovieSessionDate);
        movieSessionService.add(firstMovieSession);
        movieSessionService.add(secondMovieSession);
        System.out.println(movieSessionService.get(1L));
        movieSessionService.findAvailableSessions(secondMovieSession.getMovie().getId(),
                LocalDate.of(2023, 1, 1)).forEach(System.out::println);
    }
}
