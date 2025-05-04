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
    private static LocalDateTime movieSessionTime = LocalDateTime.of(2023, 3, 21, 12, 0);

    public static void main(String[] args) {

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);

        CinemaHall largeCinemaHall = new CinemaHall();
        largeCinemaHall.setCapacity(70);
        largeCinemaHall.setDescription("Large Hall");
        cinemaHallService.add(largeCinemaHall);
        System.out.println(cinemaHallService.get(largeCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession firstMovieSession = new MovieSession();
        firstMovieSession.setMovie(fastAndFurious);
        firstMovieSession.setCinemaHall(largeCinemaHall);
        firstMovieSession.setShowTime(movieSessionTime);

        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);

        movieSessionService.add(firstMovieSession);
        System.out.println(movieSessionService.get(firstMovieSession.getId()));
        System.out.println(movieSessionService.findAvailableSessions(firstMovieSession.getId(),
                LocalDate.from(movieSessionTime)));
    }

}
