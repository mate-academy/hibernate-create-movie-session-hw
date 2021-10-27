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
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        CinemaHall vipCinemaHall = new CinemaHall(6);
        vipCinemaHall.setDescription("It's a hall with comfort seats, where no one can "
                + "bother you during movie session");
        cinemaHallService.add(vipCinemaHall);
        System.out.println(cinemaHallService.get(vipCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession fastAndFuriousMovieSession = new MovieSession();
        fastAndFuriousMovieSession.setMovie(fastAndFurious);
        fastAndFuriousMovieSession.setCinemaHall(vipCinemaHall);
        fastAndFuriousMovieSession.setShowTime(LocalDateTime.now());
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(fastAndFuriousMovieSession);
        System.out.println(movieSessionService.get(fastAndFuriousMovieSession.getId()));
        movieSessionService.findAllAvailableSessions(fastAndFurious.getId(), LocalDate.now())
                .forEach(System.out::println);
    }
}
