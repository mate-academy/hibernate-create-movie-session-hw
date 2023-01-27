package mate.academy;

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
    private static MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie spiderMan = new Movie("Spider man");
        spiderMan.setDescription("Superhero movie about a man, who was bitten by spider");
        Movie spiderManFromDB = movieService.add(spiderMan);
        System.out.println(movieService.get(spiderManFromDB.getId()));

        CinemaHall redHall = new CinemaHall();
        redHall.setCapacity(100);
        redHall.setDescription("Small comfortable hall with unlimited beer");
        CinemaHall cinemaHallFromDB = cinemaHallService.add(redHall);
        System.out.println(cinemaHallService.get(cinemaHallFromDB.getId()));

        MovieSession movieSession = new MovieSession();
        movieSession.setShowTime(LocalDateTime.of(2023, 1, 26, 17, 0, 0));
        movieSession.setMovie(spiderManFromDB);
        movieSession.setCinemaHall(cinemaHallFromDB);
        MovieSession movieSessionFromDB = movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(movieSessionFromDB.getId()));
        System.out.println(movieSessionService.findAvailableSessions(spiderManFromDB.getId(),
                movieSessionFromDB.getShowTime().toLocalDate()));
    }
}
