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
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);
    public static void main(String[] args) {
        Movie shadows = new Movie("Shadows of Forgotten Ancestors");
        shadows.setDescription("OST - You Have Already Gone to the Other World");
        movieService.add(shadows);
        System.out.println(movieService.get(shadows.getId()));

        Movie mib = new Movie("Man in Black");
        mib.setDescription("Its not about monks");
        movieService.add(mib);
        System.out.println(mib);

        movieService.getAll().forEach(System.out::println);

        CinemaHall smallHall = new CinemaHall();
        smallHall.setCapacity(20);
        smallHall.setDescription("Special for art events");
        cinemaHallService.add(smallHall);

        CinemaHall bigHall = new CinemaHall();
        bigHall.setCapacity(50);
        bigHall.setDescription("Special for big groups");
        cinemaHallService.add(bigHall);

        System.out.println(cinemaHallService.get(bigHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession firstMovieSession = new MovieSession();
        firstMovieSession.setMovie(shadows);
        firstMovieSession.setCinemaHall(smallHall);
        firstMovieSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(firstMovieSession);

        MovieSession secondMovieSession = new MovieSession();
        secondMovieSession.setMovie(mib);
        secondMovieSession.setCinemaHall(bigHall);
        secondMovieSession.setShowTime(LocalDateTime.of(2020, 5, 25, 12, 15));
        movieSessionService.add(secondMovieSession);

        List<MovieSession> getAvailableSessions
                = movieSessionService.findAvailableSessions(1L, LocalDate.now());
        getAvailableSessions.forEach(System.out::println);
    }
}
