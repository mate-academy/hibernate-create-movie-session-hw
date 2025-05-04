package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static Injector injector = Injector.getInstance("mate");

    public static void main(String[] args) {
        Movie fightClub = new Movie();
        fightClub.setTitle("Fight club");
        fightClub.setDescription("Thriller about crazy guy");
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fightClub);
        movieService.getAll();
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(100);
        cinemaHall.setDescription("Average cinema hall");
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHall);
        cinemaHallService.getAll();
        MovieSession movieSession = new MovieSession();
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setMovie(fightClub);
        movieSession.setShowTime(LocalDateTime.of(
                LocalDate.now(), LocalTime.of(10, 15)));
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        movieSessionService.findAvailableSessions(movieSession.getId(), LocalDate.now());
    }
}
