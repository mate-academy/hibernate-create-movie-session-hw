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
    private static final Injector injector =
            Injector.getInstance("mate.academy");
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, "
                + "heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall vip = new CinemaHall();
        vip.setCapacity(12);
        vip.setDescription("Get all comfort you can imagine while "
                + "watching best movies");
        cinemaHallService.add(vip);
        System.out.println(cinemaHallService.get(vip.getId()));
        System.out.println(cinemaHallService.getAll());

        MovieSession premiere = new MovieSession();
        premiere.setCinemaHall(vip);
        premiere.setMovie(fastAndFurious);
        LocalDateTime dayAndTimeOfPremiere =
                LocalDateTime.of(2023, 4, 17, 12, 00);
        premiere.setShowTime(dayAndTimeOfPremiere);
        movieSessionService.add(premiere);
        System.out.println(movieSessionService.get(premiere.getId()));
        System.out.println(movieSessionService.findAvailableSessions(premiere.getId(),
                dayAndTimeOfPremiere.toLocalDate()));
    }
}
