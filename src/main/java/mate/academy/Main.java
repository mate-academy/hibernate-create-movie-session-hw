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
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        Movie spiderMan = new Movie("Spider-Man No Way To Home");
        spiderMan.setDescription("Movie about one of the most famous "
                + "super hero in marvel university");
        movieService.add(spiderMan);
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall alpha = new CinemaHall();
        alpha.setCapacity(48);
        alpha.setDescription("VIP hall");
        cinemaHallService.add(alpha);
        System.out.println(cinemaHallService.get(alpha.getId()));
        CinemaHall betta = new CinemaHall();
        betta.setCapacity(100);
        betta.setDescription("Good place to watch new movie");
        cinemaHallService.add(betta);
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        MovieSession first = new MovieSession(fastAndFurious,alpha);
        first.setShowTime(LocalDateTime.of(2021, 7, 16, 14, 20));
        movieSessionService.add(first);
        System.out.println(movieSessionService.get(first.getId()));
        MovieSession second = new MovieSession(fastAndFurious, betta);
        second.setShowTime(LocalDateTime.of(2021, 7, 16, 19, 10));
        movieSessionService.add(second);
        movieSessionService.findAvailableSessions(
                        fastAndFurious.getId(), LocalDate.of(2021, 7, 16))
                .forEach(System.out::println);
    }
}
