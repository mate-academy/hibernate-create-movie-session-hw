package mate.academy;

import java.time.LocalDate;
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

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        System.out.println("\n\n");

        CinemaHall smallHall = new CinemaHall(20);
        smallHall.setDescription("Small and cosy hall with 20 seats");
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(smallHall);
        System.out.println(cinemaHallService.get(smallHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        System.out.println("\n\n");

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(smallHall);
        movieSession.setShowTime(LocalDate.now().atTime(15, 0));
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(movieSession.getId()));
        LocalDate dateOfShow = LocalDate.now().plusDays(1);
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), dateOfShow)
                .forEach(System.out::println);
    }
}
