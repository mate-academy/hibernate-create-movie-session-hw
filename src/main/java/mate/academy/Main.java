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
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    private static final MovieService MOVIE_SERVICE =
            (MovieService) INJECTOR.getInstance(MovieService.class);
    private static final CinemaHallService CINEMA_HALL_SERVICE =
            (CinemaHallService) INJECTOR.getInstance(CinemaHallService.class);
    private static final MovieSessionService MOVIE_SESSION_SERVICE =
            (MovieSessionService) INJECTOR.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie avatar = new Movie("Avatar");
        avatar.setDescription("Some description.");
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");

        CinemaHall cinemaHallSmall = new CinemaHall(30);
        cinemaHallSmall.setDescription("A cinema hall that has 6 rows of 5 seats each.");
        CinemaHall cinemaHallBig = new CinemaHall(250);
        cinemaHallBig.setDescription("A cinema hall that has 20 rows of 15 seats each.");

        MovieSession movieSessionNoon = new MovieSession(fastAndFurious, cinemaHallBig);
        movieSessionNoon.setShowTime(LocalDateTime.of(2024, 2, 28, 12, 0));
        MovieSession movieSessionMorning = new MovieSession(fastAndFurious, cinemaHallSmall);
        movieSessionMorning.setShowTime(LocalDateTime.of(2024, 2, 28, 7, 0));
        MovieSession movieSessionOtherDay = new MovieSession(avatar, cinemaHallSmall);
        movieSessionOtherDay.setShowTime(LocalDateTime.of(2024, 2, 26, 12, 0));

        // Movie Service
        MOVIE_SERVICE.add(avatar);
        MOVIE_SERVICE.add(fastAndFurious);
        System.out.println(MOVIE_SERVICE.get(fastAndFurious.getId()));
        MOVIE_SERVICE.getAll().forEach(System.out::println);

        // Cinema Hall
        CINEMA_HALL_SERVICE.add(cinemaHallSmall);
        CINEMA_HALL_SERVICE.add(cinemaHallBig);
        System.out.println(CINEMA_HALL_SERVICE.get(cinemaHallBig.getId()));
        CINEMA_HALL_SERVICE.getAll().forEach(System.out::println);

        // Movie Session
        MOVIE_SESSION_SERVICE.add(movieSessionNoon);
        MOVIE_SESSION_SERVICE.add(movieSessionMorning);
        MOVIE_SESSION_SERVICE.add(movieSessionOtherDay);
        System.out.println(MOVIE_SESSION_SERVICE.get(movieSessionOtherDay.getId()));
        LocalDate date = LocalDate.of(2024, 2, 28);
        MOVIE_SESSION_SERVICE.findAvailableSessions(fastAndFurious.getId(), date)
                .forEach(System.out::println);
    }
}
