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

        Movie spiderMan = new Movie();
        spiderMan.setTitle("Spider Man");
        spiderMan.setDescription("Cool");
        movieService.add(spiderMan);

        movieService.getAll().forEach(System.out::println);

        CinemaHall good = new CinemaHall();
        good.setCapacity(300);
        good.setDescription("Very good");
        CinemaHall bad = new CinemaHall();
        bad.setCapacity(10);
        bad.setDescription("Very bad");

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(good);
        cinemaHallService.add(bad);
        cinemaHallService.get(good.getId());
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession fastAndFuriousSession = new MovieSession();
        fastAndFuriousSession.setMovie(fastAndFurious);
        fastAndFuriousSession.setCinemaHall(good);
        fastAndFuriousSession.setShowTime(LocalDateTime.of(2021, 2, 14, 15, 30));

        MovieSession spiderManSession = new MovieSession();
        spiderManSession.setMovie(spiderMan);
        spiderManSession.setCinemaHall(bad);
        spiderManSession.setShowTime(LocalDateTime.of(2019, 5, 21, 22, 0));

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(fastAndFuriousSession);
        movieSessionService.add(spiderManSession);
        movieSessionService.get(fastAndFuriousSession.getId());
        movieSessionService.findAvailableSessions(spiderMan.getId(),
                LocalDate.of(2019, 5, 21)).forEach(System.out::println);
    }
}
