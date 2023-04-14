package mate.academy;

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
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie deadAndFurious = new Movie("Dead and Furious");
        deadAndFurious.setDescription("An action film about undead assassins, fighting for good.");
        movieService.add(deadAndFurious);
        Long deadAndFuriousId = deadAndFurious.getId();
        System.out.println(movieService.get(deadAndFuriousId));
        movieService.getAll().forEach(System.out::println);

        CinemaHall evilChamber = new CinemaHall();
        evilChamber.setDescription("Evil Chamber");
        evilChamber.setCapacity(666);
        cinemaHallService.add(evilChamber);
        System.out.println(cinemaHallService.get(evilChamber.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession undeadLivesMatter = new MovieSession();
        undeadLivesMatter.setMovie(deadAndFurious);
        undeadLivesMatter.setCinemaHall(evilChamber);
        LocalDateTime localDateTime = LocalDateTime.of(2023, 4, 14, 21, 0);
        undeadLivesMatter.setShowTime(localDateTime);
        movieSessionService.add(undeadLivesMatter);
        System.out.println(movieSessionService.get(undeadLivesMatter.getId()));
        movieSessionService
                .findAvailableSessions(deadAndFuriousId, localDateTime.toLocalDate())
                .forEach(System.out::println);
    }
}
