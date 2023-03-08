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
        Movie avatarMovie = new Movie();
        avatarMovie.setDescription("Awesome film");
        avatarMovie.setTitle("Avatar 2");
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        movieService.add(avatarMovie);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(120);
        cinemaHall.setDescription("Number 1");
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHall);

        MovieSession movieSession1 = new MovieSession();
        movieSession1.setMovie(avatarMovie);
        movieSession1.setCinemaHall(cinemaHall);
        movieSession1.setShowTime(LocalDateTime.of(2023, 3, 10, 10, 12));
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession1);

        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(avatarMovie);
        movieSession2.setCinemaHall(cinemaHall);
        movieSession2.setShowTime(LocalDateTime.of(2023, 3, 11, 3, 12));
        movieSessionService.add(movieSession2);

        System.out.println(movieSessionService
                .findAvailableSessions(avatarMovie.getId(), LocalDate.of(2023, 3, 10)));
    }
}
