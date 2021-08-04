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
    private static final MovieService movieService = (MovieService) injector
            .getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService = (CinemaHallService) injector
            .getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService = (MovieSessionService) injector
            .getInstance(MovieSessionService.class);

    public static void main(String[] args) {

        Movie movie1 = new Movie("Movie1");
        Movie movie2 = new Movie("Movie2");
        Movie movie3 = new Movie("Movie3");
        movieService.add(movie1);
        movieService.add(movie2);
        movieService.add(movie3);

        CinemaHall cinemaHall1 = new CinemaHall(100, "Horror");
        CinemaHall cinemaHall2 = new CinemaHall(100, "Comedy");
        CinemaHall cinemaHall3 = new CinemaHall(100, "Action");
        cinemaHallService.add(cinemaHall1);
        cinemaHallService.add(cinemaHall2);
        cinemaHallService.add(cinemaHall3);

        MovieSession movieSession1 = new MovieSession(movie1, cinemaHall1,
                LocalDateTime.now());
        MovieSession movieSession2 = new MovieSession(movie2, cinemaHall2,
                LocalDateTime.now().plusDays(1));
        MovieSession movieSession3 = new MovieSession(movie3, cinemaHall3,
                LocalDateTime.now().plusDays(2));
        movieSessionService.add(movieSession1);
        movieSessionService.add(movieSession2);
        movieSessionService.add(movieSession3);

        System.out.println(movieSessionService.findAvailableMovieSessions(movie1.getId(),
                LocalDate.now()));
        System.out.println(cinemaHallService.getAll());
        System.out.println(movieService.get(2L));
    }
}
