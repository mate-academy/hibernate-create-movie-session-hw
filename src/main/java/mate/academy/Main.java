package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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
        Injector injector = Injector.getInstance("mate.academy");

        // Testing MovieService methods
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println("Movie added: " + fastAndFurious);
        Movie retrievedMovie = movieService.get(fastAndFurious.getId());
        System.out.println("Retrieved Movie: " + retrievedMovie);
        List<Movie> allMovies = movieService.getAll();
        System.out.println("All Movies: " + allMovies);

        // Testing CinemaHallService methods
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        CinemaHall cinemaHall = new CinemaHall("Hall 1", 100);
        cinemaHallService.add(cinemaHall);
        System.out.println("Cinema Hall added: " + cinemaHall);
        CinemaHall retrievedCinemaHall = cinemaHallService.get(cinemaHall.getId());
        System.out.println("Retrieved Cinema Hall: " + retrievedCinemaHall);
        List<CinemaHall> allCinemaHalls = cinemaHallService.getAll();
        System.out.println("All Cinema Halls: " + allCinemaHalls);

        // Testing MovieSessionService methods
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        MovieSession movieSession = new MovieSession(fastAndFurious, cinemaHall, date);
        movieSessionService.add(movieSession);
        System.out.println("Movie Session added: " + movieSession);
        MovieSession retrievedMovieSession = movieSessionService.get(movieSession.getId());
        System.out.println("Retrieved Movie Session: " + retrievedMovieSession);
        List<MovieSession> availableSessions = movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDate.now());
        System.out.println("Available Movie Sessions: " + availableSessions);
    }
}
