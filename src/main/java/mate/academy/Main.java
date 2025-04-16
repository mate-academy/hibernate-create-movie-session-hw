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
    public static void main(String[] args) {
        Movie movie = new Movie();
        movie.setTitle("Movie123");
        movie.setDescription("Good movie");
        Injector injector = Injector.getInstance("mate.academy");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(movie);
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(2000);
        cinemaHall.setDescription("Big cinema");
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHall);
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(movie);
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setShowTime(LocalDateTime.now());
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        List<MovieSession> availableSessions = movieSessionService.findAvailableSessions(
                movie.getId(), LocalDate.now());
        System.out.println(availableSessions);
    }
}
