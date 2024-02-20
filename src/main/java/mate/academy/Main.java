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
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final MovieService movieService = (MovieService) injector
            .getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService = (CinemaHallService) injector
            .getInstance(CinemaHall.class);
    private static final MovieSessionService movieSessionService = (MovieSessionService) injector
            .getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie bobMarley = new Movie("Bob Marley: One Love (2024)");
        bobMarley.setDescription("Marley is about the ups and downs "
                + "associated with his revolutionary music.");
        movieService.add(bobMarley);
        System.out.println(movieService.get(bobMarley.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall secondCinemaHall = new CinemaHall();
        secondCinemaHall.setCapacity(49);
        secondCinemaHall.setDescription("Blockbuster cinema. Hall: 2");
        cinemaHallService.add(secondCinemaHall);
        System.out.println(cinemaHallService.get(secondCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession firstMovieSession = new MovieSession();
        firstMovieSession.setMovie(bobMarley);
        firstMovieSession.setCinemaHall(secondCinemaHall);
        LocalDate dateShowBobMarley = LocalDate.of(2024, 2, 20);
        LocalTime timeShowBobMarley = LocalTime.of(14, 10);
        firstMovieSession.setShowTime(LocalDateTime.of(dateShowBobMarley, timeShowBobMarley));
        movieSessionService.add(firstMovieSession);
        System.out.println(movieSessionService.get(firstMovieSession.getId()));
        movieSessionService.findAvailableSessions(firstMovieSession.getId(), dateShowBobMarley)
                .forEach(System.out::println);
    }
}
