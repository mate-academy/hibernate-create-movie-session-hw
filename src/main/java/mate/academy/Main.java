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
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);

    public static void main(String[] args) {

        Movie fastAndFurious = new Movie("Fast and Furious");
        Movie homeAlone = new Movie("Home Alone");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        homeAlone.setDescription("Family comedy movie");
        movieService.add(fastAndFurious);
        movieService.add(homeAlone);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall smallCinemaHall = new CinemaHall();
        CinemaHall bigCinemaHall = new CinemaHall();
        smallCinemaHall.setCapacity(50);
        smallCinemaHall.setDescription("Small cinema hall");
        bigCinemaHall.setCapacity(100);
        bigCinemaHall.setDescription("Big cinema hall");
        cinemaHallService.add(smallCinemaHall);
        cinemaHallService.add(bigCinemaHall);
        System.out.println(cinemaHallService.get(smallCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession fastAndFuriousSession = new MovieSession();
        fastAndFuriousSession.setCinemaHall(bigCinemaHall);
        fastAndFuriousSession.setMovie(fastAndFurious);
        fastAndFuriousSession.setShowTime(
                LocalDateTime.of(2023, Month.AUGUST, 26, 19,30));
        movieSessionService.add(fastAndFuriousSession);
        System.out.println(movieSessionService.get(fastAndFuriousSession.getId()));
        movieSessionService.findAvailableSessions(fastAndFuriousSession.getId(),
                LocalDate.from(LocalDateTime.of(
                        2023, Month.AUGUST, 26, 19,30)));
    }
}
