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

    public static void main(String[] args) {
        final MovieService movieService = (MovieService) injector
                .getInstance(MovieService.class);
        final CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);

        Movie oppenheimer = new Movie("Oppenheimer");
        oppenheimer.setDescription("Now I'm become death");
        Movie barbie = new Movie("Barbie");
        barbie.setDescription("I'm KENOUGH");
        movieService.add(oppenheimer);
        movieService.add(barbie);

        CinemaHall smallHall = new CinemaHall();
        smallHall.setDescription("SMALL HALL");
        smallHall.setCapacity(100);

        CinemaHall bigHall = new CinemaHall();
        bigHall.setDescription("BIG HALL");
        bigHall.setCapacity(350);
        cinemaHallService.add(smallHall);
        cinemaHallService.add(bigHall);

        MovieSession firstMovieSession = new MovieSession();
        firstMovieSession.setMovie(movieService.get(1L));
        firstMovieSession.setCinemaHall(cinemaHallService.get(2L).orElseThrow());
        firstMovieSession.setShowTime(LocalDateTime.of(2023, 8, 8, 12, 30));

        MovieSession secondMovieSession = new MovieSession();
        secondMovieSession.setMovie(movieService.get(2L));
        secondMovieSession.setCinemaHall(cinemaHallService.get(1L).orElseThrow());
        secondMovieSession.setShowTime(LocalDateTime.of(2023, 9, 8, 14, 30));
        movieSessionService.add(firstMovieSession);
        movieSessionService.add(secondMovieSession);

        System.out.println(movieSessionService.findAvailableSessions(2L, LocalDate.of(2023, 9, 8)));

    }
}
