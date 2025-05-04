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
    private static final Injector injector = Injector.getInstance(
            "mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie movie = new Movie();
        movie.setTitle("Inception");
        movieService.add(movie);
        System.out.println("Added movie: " + movie);
        Movie movieFromDb = movieService.get(movie.getId());
        System.out.println("Movie from DB: " + movieFromDb);
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(150);
        cinemaHall.setDescription("IMAX Cinema Hall");
        cinemaHallService.add(cinemaHall);
        System.out.println("Added cinema hall: " + cinemaHall);
        CinemaHall cinemaHallFromDb = cinemaHallService.get(cinemaHall.getId());
        System.out.println("Cinema Hall from DB: " + cinemaHallFromDb);
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(movieFromDb);
        movieSession.setCinemaHall(cinemaHallFromDb);
        LocalDateTime today = LocalDateTime.now();
        movieSession.setShowTime(today);
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        System.out.println("Added movie session: " + movieSession);
        MovieSession movieSessionFromDb = movieSessionService.get(movieSession.getId());
        System.out.println("Movie Session from DB: " + movieSessionFromDb);
        List<CinemaHall> cinemaHalls = cinemaHallService.getAll();
        System.out.println("All cinema halls: " + cinemaHalls);
        List<Movie> movies = movieService.getAll();
        System.out.println("All movies: " + movies);
        List<MovieSession> availableSessions = movieSessionService.findAvailableSessions(
                movie.getId(), LocalDate.now());
        System.out.println("Available movie sessions for today: " + availableSessions);
    }
}
