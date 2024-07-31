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

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious
                .setDescription("An action film about street racing, heists, and spies.");

        Movie superMan = new Movie("Super Man");
        superMan.setDescription("An action film about man with the big power.");

        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);

        movieService.add(fastAndFurious);
        movieService.add(superMan);

        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall1 =
                new CinemaHall(100, "The biggest 3D cinema hall");
        CinemaHall cinemaHall2 =
                new CinemaHall(50, "Small 3D cinema hall");

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        cinemaHallService.add(cinemaHall1);
        cinemaHallService.add(cinemaHall2);

        System.out.println(cinemaHallService.get(cinemaHall1.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSession1 =
                new MovieSession(superMan, cinemaHall1,
                        LocalDateTime.of(2024, 7,
                                31, 15, 30));
        MovieSession movieSession2 =
                new MovieSession(superMan, cinemaHall1,
                        LocalDateTime.of(2024, 7,
                                31, 17, 40));
        MovieSession movieSession3 =
                new MovieSession(superMan, cinemaHall2,
                        LocalDateTime.of(2024, 8,
                                31, 0, 0));

        MovieSession movieSession4 =
                new MovieSession(fastAndFurious, cinemaHall2,
                        LocalDateTime.of(2024, 7,
                                31, 15, 30));
        MovieSession movieSession5 =
                new MovieSession(fastAndFurious, cinemaHall2,
                        LocalDateTime.of(2024, 7,
                                31, 17, 40));
        MovieSession movieSession6 =
                new MovieSession(fastAndFurious, cinemaHall1,
                        LocalDateTime.of(2024, 8,
                                31, 0, 0));

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        movieSessionService.add(movieSession1);
        movieSessionService.add(movieSession2);
        movieSessionService.add(movieSession3);
        movieSessionService.add(movieSession4);
        movieSessionService.add(movieSession5);
        movieSessionService.add(movieSession6);

        System.out.println(movieSessionService.get(movieSession1.getId()));

        LocalDate specificDate = LocalDate.of(2024, 8, 31);

        movieSessionService
                .findAvailableSessions(superMan.getId(), specificDate)
                .forEach(System.out::println);
    }
}
