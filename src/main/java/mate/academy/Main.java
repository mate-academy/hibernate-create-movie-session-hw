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
    private static final MovieService MOVIE_SERVICE = (MovieService) INJECTOR
            .getInstance(MovieService.class);
    private static final CinemaHallService CINEMA_HALL_SERVICE = (CinemaHallService) INJECTOR
            .getInstance(CinemaHallService.class);
    private static final MovieSessionService MOVIE_SESSION_SERVICE = (MovieSessionService) INJECTOR
            .getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie terminator = new Movie("Terminator 2");
        terminator.setDescription("Return of the terminator on liquid metal.");
        MOVIE_SERVICE.add(fastAndFurious);
        MOVIE_SERVICE.add(terminator);
        System.out.println(MOVIE_SERVICE.get(fastAndFurious.getId()));
        MOVIE_SERVICE.getAll().forEach(System.out::println);

        CinemaHall bigHall = new CinemaHall(250);
        bigHall.setDescription("Big hall for 250 place");
        CinemaHall smallHall = new CinemaHall(100);
        smallHall.setDescription("Small hall for 100 place");
        CINEMA_HALL_SERVICE.add(bigHall);
        CINEMA_HALL_SERVICE.add(smallHall);
        System.out.println(CINEMA_HALL_SERVICE.get(bigHall.getId()));
        CINEMA_HALL_SERVICE.getAll().forEach(System.out::println);

        MovieSession morningSession = new MovieSession();
        morningSession.setMovie(fastAndFurious);
        morningSession.setCinemaHall(bigHall);
        morningSession.setShowTime(LocalDateTime.now());

        MOVIE_SESSION_SERVICE.add(morningSession);
        MovieSession daytimeSession = new MovieSession();
        daytimeSession.setMovie(fastAndFurious);
        daytimeSession.setCinemaHall(bigHall);
        daytimeSession.setShowTime(LocalDateTime.now().minusDays(2L));
        MOVIE_SESSION_SERVICE.add(daytimeSession);

        MovieSession eveningSession = new MovieSession();
        eveningSession.setMovie(terminator);
        eveningSession.setCinemaHall(smallHall);
        eveningSession.setShowTime(LocalDateTime.now());
        MOVIE_SESSION_SERVICE.add(eveningSession);
        System.out.println(MOVIE_SESSION_SERVICE.get(daytimeSession.getId()));
        MOVIE_SESSION_SERVICE.findAvailableSessions(1L, LocalDate.now())
                        .forEach(System.out::println);
    }
}
