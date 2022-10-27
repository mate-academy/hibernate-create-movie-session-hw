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
        Movie avatar = new Movie("Avatar");
        avatar.setDescription("science fiction/action");
        movieService.add(avatar);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall smallCinemaHall = new CinemaHall(100, "small hall");
        CinemaHall bigCinemaHall = new CinemaHall(300, "big hall");
        cinemaHallService.add(smallCinemaHall);
        cinemaHallService.add(bigCinemaHall);
        System.out.println(cinemaHallService.get(smallCinemaHall.getId()));
        System.out.println(cinemaHallService.getAll());

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        MovieSession avatarMovieSession =
                new MovieSession(avatar, smallCinemaHall,
                        LocalDateTime.of(2022, 10, 19, 21, 10));
        MovieSession fastAndFuriousMovieSession =
                new MovieSession(fastAndFurious, bigCinemaHall,
                        LocalDateTime.of(2022, 10, 19, 15, 35));
        movieSessionService.add(avatarMovieSession);
        movieSessionService.add(fastAndFuriousMovieSession);
        System.out.println(movieSessionService.get(fastAndFuriousMovieSession.getId()));
        System.out.println(movieSessionService.findAvailableSessions(2L, LocalDate.now()));
    }
}
