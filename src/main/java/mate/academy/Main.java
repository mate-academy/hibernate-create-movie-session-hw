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

    public static void main(String[] args) {
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);

        Movie movie = new Movie();
        movie.setTitle("Avatar");
        movie.setDescription("Sci-fi");

        movie = movieService.add(movie);

        System.out.println(movieService.get(movie.getId()));
        System.out.println(movieService.getAll());

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(
                        CinemaHallService.class);

        CinemaHall hall = new CinemaHall();
        hall.setCapacity(120);
        hall.setDescription("Blue hall");

        hall = cinemaHallService.add(hall);

        System.out.println(cinemaHallService.get(hall.getId()));
        System.out.println(cinemaHallService.getAll());

        final MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(
                        MovieSessionService.class);

        MovieSession session = new MovieSession();
        session.setMovie(movie);
        session.setCinemaHall(hall);
        session.setShowTime(LocalDateTime.now());

        session = movieSessionService.add(session);

        System.out.println(movieSessionService.get(session.getId()));

        System.out.println(
                movieSessionService.findAvailableSessions(
                        movie.getId(),
                        LocalDate.now()));
    }
}

