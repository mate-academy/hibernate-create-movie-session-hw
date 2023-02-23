package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
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

        final MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        final CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        Movie movieTerminator = new Movie("Terminator", "Super");
        final Movie movieMatix = new Movie("Martix", "Cool");
        Movie moviefastAndFurious = new Movie("Fast and Furious");
        moviefastAndFurious.setDescription(
                "An action film about street racing, heists, and spies.");

        movieService.add(moviefastAndFurious);
        movieService.add(movieTerminator);
        movieService.add(movieMatix);

        CinemaHall smallHall = new CinemaHall(100, "Small hall");
        CinemaHall midleHall = new CinemaHall(150, "Midl hall");
        CinemaHall ladgeHall = new CinemaHall(200, "Ladge");

        cinemaHallService.add(smallHall);
        cinemaHallService.add(midleHall);
        cinemaHallService.add(ladgeHall);

        MovieSession movieSession1 = new MovieSession(movieTerminator, smallHall,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 30, 0)));
        MovieSession movieSession2 = new MovieSession(movieTerminator, midleHall,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(5, 30, 0)));
        MovieSession movieSession3 = new MovieSession(movieTerminator, ladgeHall,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 30, 0)));
        MovieSession movieSession4 = new MovieSession(movieTerminator, ladgeHall,
                LocalDateTime.of(LocalDate.of(2023, 4, 2), LocalTime.of(11, 30, 0)));
        MovieSession movieSession5 = new MovieSession(movieTerminator, ladgeHall,
                LocalDateTime.of(LocalDate.of(2023, 2, 22), LocalTime.of(0, 0, 0)));

        movieSessionService.add(movieSession1);
        movieSessionService.add(movieSession2);
        movieSessionService.add(movieSession3);
        movieSessionService.add(movieSession4);
        movieSessionService.add(movieSession5);

        //        System.out.println(movieService.get(movieMatix.getId()));
        //        System.out.println(movieService.get(movieTerminator.getId()));
        //        System.out.println(movieService.get(moviefastAndFurious.getId()));
        //        movieService.getAll().forEach(System.out::println);

        List<MovieSession> movieSessions =
                movieSessionService.findAvailableSessions(2L, LocalDate.now());
        movieSessions.forEach(x -> System.out.println(x));

        // System.out.println(movieSessionService.get(1L));
    }
}
