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

    public static void main(String[] args) {
        final MovieService movieService = (MovieService) injector
                .getInstance(MovieService.class);

        final MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);

        final CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall pravdaKino = new CinemaHall(350);
        pravdaKino.setDescription("This is the one of oldest cinemaHall of Dnipro");
        cinemaHallService.add(pravdaKino);
        System.out.println(cinemaHallService.get(pravdaKino.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        LocalDateTime sessionDateTime = LocalDateTime.parse("2021-07-13T13:17");
        MovieSession movieSession = new MovieSession(fastAndFurious);
        movieSession.setCinemaHall(pravdaKino);
        movieSession.setShowTime(sessionDateTime);
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(movieSession.getId()));
        movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), sessionDateTime.toLocalDate());
    }
}
