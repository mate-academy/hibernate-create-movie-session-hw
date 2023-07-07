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
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    private static final CinemaHallService cinemaHallService = (CinemaHallService)
            INJECTOR.getInstance(CinemaHallService.class);
    private static final MovieService movieService = (MovieService)
            INJECTOR.getInstance(MovieService.class);
    private static final MovieSessionService movieSessionService = (MovieSessionService)
            INJECTOR.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious",
                "An action film about street racing, heists, and spies.");
        Movie interstellar = new Movie("Interstellar",
                "Cool movie");
        movieService.add(fastAndFurious);
        movieService.add(interstellar);
        System.out.println(movieService.get(interstellar.getId()));
        movieService.getAll().forEach(System.out::println);

        System.out.println();
        CinemaHall fastAndFuriousCinemaHall = new CinemaHall(20, fastAndFurious.getTitle());
        CinemaHall interstellarsCinemaHall = new CinemaHall(30, interstellar.getTitle());
        cinemaHallService.add(fastAndFuriousCinemaHall);
        cinemaHallService.add(interstellarsCinemaHall);
        System.out.println(cinemaHallService.get(interstellarsCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        System.out.println();
        MovieSession fastAndFuriousMovieSession = new MovieSession(fastAndFurious,
                fastAndFuriousCinemaHall,
                LocalDateTime.of(2023, 7, 8, 12, 30));
        MovieSession interstellarMovieSession = new MovieSession(interstellar,
                interstellarsCinemaHall,
                LocalDateTime.of(2023, 7, 8, 17, 0));
        MovieSession interstellarMovieSession2 = new MovieSession(interstellar,
                interstellarsCinemaHall,
                LocalDateTime.of(2023, 7, 9, 17, 0));
        MovieSession interstellarMovieSession3 = new MovieSession(interstellar,
                fastAndFuriousCinemaHall,
                LocalDateTime.of(2023, 7, 9, 21, 0));
        movieSessionService.add(fastAndFuriousMovieSession);
        movieSessionService.add(interstellarMovieSession);
        movieSessionService.add(interstellarMovieSession2);
        movieSessionService.add(interstellarMovieSession3);
        System.out.println(movieSessionService.get(interstellarMovieSession.getId()));
        System.out.println("08.07.2023");
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2023, 7, 8)).forEach(System.out::println);
        movieSessionService.findAvailableSessions(interstellar.getId(),
                LocalDate.of(2023, 7, 8)).forEach(System.out::println);
        System.out.println("09.07.2023");
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2023, 7, 9)).forEach(System.out::println);
        movieSessionService.findAvailableSessions(interstellar.getId(),
                LocalDate.of(2023, 7, 9)).forEach(System.out::println);
    }
}
