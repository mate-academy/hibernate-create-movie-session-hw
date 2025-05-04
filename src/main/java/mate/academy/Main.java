package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {

        Movie movie = new Movie();
        movie.setTitle("The little women");
        movie.setDescription("A good movie");

        MovieService movieService = (MovieService) INJECTOR.getInstance(MovieService.class);
        movieService.add(movie);
        System.out.println(movieService.get(movie.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(100);
        cinemaHall.setDescription("First Hall");

        CinemaService cinemaService = (CinemaService) INJECTOR.getInstance(CinemaService.class);
        cinemaService.add(cinemaHall);
        System.out.println(cinemaService.get(cinemaHall.getId()));
        cinemaService.getAll().forEach(System.out::println);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(movie);
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setShowTime(LocalDateTime.now());

        MovieSessionService movieSessionService = (MovieSessionService)
                INJECTOR.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(movieSession.getId()));
        movieSessionService.findAvailableSessions(movieSession.getId(),
                        LocalDate.now()).forEach(System.out::println);

    }
}
