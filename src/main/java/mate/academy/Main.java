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
        MovieService movieService = (MovieService)
                injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);

        CinemaHall cinemaHall = new CinemaHall(20, "Very cool cinema Hall");
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        LocalDate localDate = LocalDate.of(2023, 6, 23);
        MovieSession movieSession = new MovieSession(fastAndFurious, cinemaHall, localDate);
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(movieSession.getId()));
        movieSessionService.findAvailableSessions(
                fastAndFurious.getId(), localDate).forEach(System.out::println);
    }
}
