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
        Movie transformers = new Movie("Transformers");
        transformers.setDescription("An action film about transformers fom another galaxy");
        movieService.add(fastAndFurious);
        movieService.add(transformers);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall hallOne = new CinemaHall(100, "hallOne");
        CinemaHall hallTwo = new CinemaHall(200, "hallTwo");
        cinemaHallService.add(hallOne);
        cinemaHallService.add(hallTwo);
        System.out.println(cinemaHallService.get(hallOne.getId()));
        System.out.println(cinemaHallService.getAll());

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        MovieSession movieSessionOne =
                new MovieSession(fastAndFurious, hallOne,
                        LocalDateTime.of(2025, 1, 31, 17, 00));
        MovieSession movieSessionTwo =
                new MovieSession(fastAndFurious, hallOne,
                        LocalDateTime.of(2025, 1, 31, 22, 00));
        MovieSession movieSessionThree =
                new MovieSession(transformers, hallTwo,
                        LocalDateTime.of(2025, 1, 31, 18, 30));
        movieSessionService.add(movieSessionOne);
        movieSessionService.add(movieSessionTwo);
        movieSessionService.add(movieSessionThree);
        System.out.println(movieSessionService.get(movieSessionTwo.getId()));
        movieSessionService.findAvailableSessions(
                        transformers.getId(), LocalDate.of(2025, 1, 31))
                .forEach(System.out::println);
    }
}
