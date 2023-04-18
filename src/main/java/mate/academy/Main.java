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
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie marvel = new Movie("Marvel");
        marvel.setDescription("Fantasy film");
        movieService.add(fastAndFurious);
        movieService.add(marvel);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall firstHall = new CinemaHall();
        firstHall.setCapacity(100);
        firstHall.setDescription("this hall can keep up to 100 visitors");
        cinemaHallService.add(firstHall);
        CinemaHall secondHall = new CinemaHall();
        secondHall.setCapacity(61);
        secondHall.setDescription("this hall can keep up to 60 visitors");
        cinemaHallService.add(secondHall);
        System.out.println(cinemaHallService.get(firstHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession firstSession = new MovieSession();
        firstSession.setMovie(fastAndFurious);
        firstSession.setCinemaHall(firstHall);
        LocalDateTime localDateTime = LocalDateTime.of(2023, 4, 17, 19, 0, 0);
        firstSession.setShowTime(localDateTime);
        movieSessionService.add(firstSession);
        movieSessionService.get(firstSession.getId());
        System.out.println(movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), localDateTime.toLocalDate()));
    }
}
