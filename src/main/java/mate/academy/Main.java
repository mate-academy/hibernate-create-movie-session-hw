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
        Movie fastAndFurious = new Movie("Fast and Furious",
                "An action film about street racing, heists, and spies.");
        Movie hachiko = new Movie("Hachiko", "Drama film about very devoted dog");
        movieService.add(fastAndFurious);
        movieService.add(hachiko);

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        CinemaHall firstHall = new CinemaHall(250,"first hall");
        CinemaHall secondHall = new CinemaHall(120, "second hall");
        cinemaHallService.add(firstHall);
        cinemaHallService.add(secondHall);

        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        MovieSession movieSession1 = new MovieSession(fastAndFurious, firstHall,
                LocalDateTime.of(2021,12, 27, 12, 30));
        MovieSession movieSession2 = new MovieSession(hachiko, secondHall,
                LocalDateTime.of(2021,12, 27, 0, 28));
        movieSessionService.add(movieSession1);
        movieSessionService.add(movieSession2);

        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        movieSessionService.findAvailableSessions(hachiko.getId(), LocalDate.now())
                .forEach(System.out::println);

    }
}
