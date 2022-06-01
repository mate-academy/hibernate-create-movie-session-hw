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
    private static Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(1);
        cinemaHall.setDescription("Cinema booth");
        CinemaHall addedCinemaHall = cinemaHallService.add(cinemaHall);
        cinemaHallService.get(addedCinemaHall.getId());
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setShowTime(LocalDate.now());
        movieSession.setCinemaHall(cinemaHall);
        MovieSession addedMovieSession = movieSessionService.add(movieSession);
        movieSessionService.get(addedMovieSession.getId());
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), getLocalDate());
    }

    private static LocalDate getLocalDate() {
        return LocalDate.of(2022,1,1);
    }
}
