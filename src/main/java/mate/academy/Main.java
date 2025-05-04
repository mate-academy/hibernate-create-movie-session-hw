package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final MovieService movieService = (MovieService)
            injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);
    private static final String MOVIE_TITLE_1 = "Fast and Furious";
    private static final String MOVIE_DESCRIPTION_1 =
            "An action film about street racing, heists, and spies.";
    private static final int CINEMA_HALL_CAPACITY_1 = 100;
    private static final String CINEMA_HALL_DESCRIPTION_1 = "It is equipped with a "
            + "digital projector \"Barco\" for watching 3D movies, the acoustic system "
            + "of the cinema hall is JBL, and the digital sound processor is DolbyDigital.";
    private static final LocalDateTime MOVIE_SESSION_DATE_TIME_1 =
            LocalDateTime.of(2023, Month.APRIL, 16, 14, 30);
    private static final LocalDate FIND_MOVIE_SESSION_BY_DATE =
            LocalDate.of(2023, Month.APRIL, 16);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie(MOVIE_TITLE_1);
        fastAndFurious.setDescription(MOVIE_DESCRIPTION_1);
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall redCinemaHall =
                new CinemaHall(CINEMA_HALL_CAPACITY_1, CINEMA_HALL_DESCRIPTION_1);
        cinemaHallService.add(redCinemaHall);
        System.out.println(cinemaHallService.get(redCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSession =
                new MovieSession(fastAndFurious, redCinemaHall, MOVIE_SESSION_DATE_TIME_1);
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(movieSession.getId()));
        System.out.println(movieSessionService.findAvailableSessions(1L,
                FIND_MOVIE_SESSION_BY_DATE));
    }
}
