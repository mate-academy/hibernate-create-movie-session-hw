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
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and furious");
        fastAndFurious.setDescription("fast");
        movieService.add(fastAndFurious);

        CinemaHall firstHall = new CinemaHall();
        firstHall.setCapacity(100);
        firstHall.setDescription("Ploho");
        cinemaHallService.add(firstHall);

        CinemaHall secondHall = new CinemaHall();
        secondHall.setCapacity(200);
        secondHall.setDescription("Xorosho");
        cinemaHallService.add(secondHall);

        MovieSession firstMovieSession = new MovieSession();
        firstMovieSession.setMovie(fastAndFurious);
        firstMovieSession.setCinemaHall(firstHall);
        firstMovieSession.setShowTime(LocalDateTime.now());

        MovieSession secondMovieSession = new MovieSession();
        secondMovieSession.setMovie(fastAndFurious);
        secondMovieSession.setCinemaHall(secondHall);
        secondMovieSession.setShowTime(LocalDateTime.now());

        movieSessionService.add(firstMovieSession);
        movieSessionService.add(secondMovieSession);
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.now());
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(movieService.getAll());
        System.out.println(cinemaHallService.get(firstHall.getId()));
        System.out.println(cinemaHallService.getAll());
        System.out.println(movieSessionService.get(firstMovieSession.getId()));
        System.out.println(movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDate.now()));
    }
}
