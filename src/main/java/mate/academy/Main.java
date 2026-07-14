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

        Injector injector =
                Injector.getInstance("mate.academy");

        final MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);

        final CinemaHallService cinemaHallService =
                (CinemaHallService)
                        injector.getInstance(CinemaHallService.class);

        final MovieSessionService movieSessionService =
                (MovieSessionService)
                        injector.getInstance(MovieSessionService.class);

        Movie movie = new Movie();
        movie.setTitle("Interstellar");
        movie.setDescription("Sci-Fi");
        movieService.add(movie);

        CinemaHall hall = new CinemaHall()
                .setDescription("Main Hall")
                .setCapacity(120);

        cinemaHallService.add(hall);

        MovieSession session = new MovieSession()
                .setMovie(movie)
                .setCinemaHall(hall)
                .setShowTime(LocalDateTime.now());

        movieSessionService.add(session);

        System.out.println("Movie:");
        System.out.println(movieService.get(movie.getId()));

        System.out.println("Cinema Hall:");
        System.out.println(cinemaHallService.get(hall.getId()));

        System.out.println("Sessions:");

        List<MovieSession> sessions =
                movieSessionService.findAvailableSessions(
                        movie.getId(),
                        LocalDate.now());

        System.out.println(sessions.size());
    }
}
