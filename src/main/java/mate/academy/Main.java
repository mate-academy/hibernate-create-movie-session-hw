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
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie sw5 = new Movie("Star Wars: Episode V");
        sw5.setDescription("Talking frog convinces son to kill his dad.");

        movieService.add(fastAndFurious);
        movieService.add(sw5);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall mainHall = new CinemaHall(200);
        mainHall.setDescription("The main cinema hall.");
        CinemaHall vipHall = new CinemaHall(10);
        vipHall.setDescription("Private hall for VIPs");

        cinemaHallService.add(mainHall);
        cinemaHallService.add(vipHall);
        System.out.println(cinemaHallService.get(vipHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession fastNFuriousSession1 =
                new MovieSession(fastAndFurious, mainHall, LocalDateTime.now());
        MovieSession fastNFuriousSession2 =
                new MovieSession(fastAndFurious, mainHall, LocalDateTime.now().plusHours(3));
        MovieSession fastNFuriousSessionTomorrow =
                new MovieSession(fastAndFurious, mainHall, LocalDateTime.now().plusDays(1));
        MovieSession fastNFuriousVipSession =
                new MovieSession(fastAndFurious, vipHall, LocalDateTime.now().minusHours(3));
        MovieSession sw5SessionMainHall =
                new MovieSession(sw5, mainHall, LocalDateTime.now().minusHours(4));
        MovieSession sw5SessionVip =
                new MovieSession(sw5, vipHall, LocalDateTime.now().plusMinutes(30));
        MovieSession sw5SessionTomorrow =
                new MovieSession(sw5, vipHall, LocalDateTime.now().plusDays(1));

        movieSessionService.add(fastNFuriousSession1);
        movieSessionService.add(fastNFuriousSession2);
        movieSessionService.add(fastNFuriousVipSession);
        movieSessionService.add(fastNFuriousSessionTomorrow);
        movieSessionService.add(sw5SessionMainHall);
        movieSessionService.add(sw5SessionVip);
        movieSessionService.add(sw5SessionTomorrow);
        System.out.println(movieSessionService.get(fastNFuriousSession1.getId()));
        movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDate.now())
                .forEach(System.out::println);
        movieSessionService
                .findAvailableSessions(sw5.getId(), LocalDate.now().plusDays(1))
                .forEach(System.out::println);
    }
}
