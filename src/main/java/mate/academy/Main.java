package mate.academy;

import java.time.LocalDateTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall firstCinemaHall = new CinemaHall();
        firstCinemaHall.setCapacity(100);
        firstCinemaHall.setDescription("Pretty good");

        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(firstCinemaHall);
        System.out.println(cinemaHallService.get(firstCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        LocalDateTime demoDateTime = LocalDateTime.of(2018, 3, 4, 14, 45);

        MovieSession fastAndFuriousMovieSession = new MovieSession();
        fastAndFuriousMovieSession.setMovie(fastAndFurious);
        fastAndFuriousMovieSession.setShowTime(demoDateTime);
        fastAndFuriousMovieSession.setCinemaHall(firstCinemaHall);

        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        movieSessionService.add(fastAndFuriousMovieSession);
        System.out.println(movieSessionService.get(fastAndFuriousMovieSession.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                demoDateTime.toLocalDate()).forEach(System.out::println);
    }
}
