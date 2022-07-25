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
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        Movie gentlemen = new Movie("Gentlemen");
        gentlemen.setDescription("Film by Guy Ritchie");
        movieService.add(gentlemen);
        movieService.getAll().forEach(System.out::println);

        CinemaHall vipHall = new CinemaHall();
        vipHall.setCapacity(15);
        vipHall.setDescription("VIP cinema hall");
        cinemaHallService.add(vipHall);

        CinemaHall simpleHall = new CinemaHall();
        simpleHall.setCapacity(50);
        simpleHall.setDescription("Simple cinema hall");
        cinemaHallService.add(simpleHall);

        MovieSession gentlemenMovieSession = new MovieSession();
        gentlemenMovieSession.setCinemaHall(vipHall);
        gentlemenMovieSession.setMovie(gentlemen);
        gentlemenMovieSession.setShowTime(LocalDateTime
                .of(2022,7,28,14,30));
        movieSessionService.add(gentlemenMovieSession);
        movieSessionService.get(gentlemenMovieSession.getId());

        MovieSession fastAndFuriousMovieSession = new MovieSession();
        fastAndFuriousMovieSession.setCinemaHall(simpleHall);
        fastAndFuriousMovieSession.setMovie(fastAndFurious);
        fastAndFuriousMovieSession.setShowTime(LocalDateTime
                .of(2022,7,30,15,0));
        movieSessionService.add(fastAndFuriousMovieSession);
    }
}
