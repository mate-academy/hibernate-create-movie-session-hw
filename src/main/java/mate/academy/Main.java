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
    private static CinemaHallService cinemaHallService;
    private static MovieService movieService;
    private static MovieSessionService movieSessionService;

    public static void main(String[] args) {
        cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        movieService =
                (MovieService) injector.getInstance(MovieService.class);
        movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie frozen = new Movie();
        frozen.setTitle("Frozen");
        frozen.setDescription("Family type movie");
        movieService.add(frozen);
        System.out.println(frozen.getId());
        movieService.getAll().forEach(System.out::println);

        CinemaHall redHall = new CinemaHall();
        redHall.setCapacity(322);
        redHall.setDescription("---> Here could be your add <----");
        cinemaHallService.add(redHall);
        cinemaHallService.get(redHall.getId());

        CinemaHall blueHall = new CinemaHall();
        blueHall.setCapacity(223);
        blueHall.setDescription("6+ age is allowed");
        cinemaHallService.add(blueHall);
        cinemaHallService.get(blueHall.getId());
        cinemaHallService.getAll();

        MovieSession fastAndFuriousSession = new MovieSession();
        fastAndFuriousSession.setCinemaHall(redHall);
        fastAndFuriousSession.setMovie(fastAndFurious);
        fastAndFuriousSession.setShowTime(
                LocalDateTime.of(2022, 7, 21, 15, 30));
        movieSessionService.add(fastAndFuriousSession);
        movieSessionService.get(fastAndFuriousSession.getId());

        MovieSession frozenSessionMorning = new MovieSession();
        frozenSessionMorning.setMovie(frozen);
        frozenSessionMorning.setCinemaHall(blueHall);
        frozenSessionMorning.setShowTime(
                LocalDateTime.of(2022, 7, 28, 9, 15));
        movieSessionService.add(frozenSessionMorning);
        movieSessionService.get(frozenSessionMorning.getId());

        MovieSession frozenSessionAfternoon = new MovieSession();
        frozenSessionMorning.setMovie(frozen);
        frozenSessionMorning.setCinemaHall(redHall);
        frozenSessionMorning.setShowTime(
                LocalDateTime.of(2022, 7, 28, 15, 15));
        movieSessionService.add(frozenSessionMorning);
        System.out.println(movieSessionService.findAvailableSessions(frozenSessionMorning.getId(),
                LocalDate.of(2022, 7, 28)));
    }
}
