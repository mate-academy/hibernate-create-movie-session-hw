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
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall yellowHall = new CinemaHall();
        yellowHall.setCapacity(50);
        cinemaHallService.add(yellowHall);

        CinemaHall greenHall = new CinemaHall();
        greenHall.setCapacity(100);
        cinemaHallService.add(greenHall);

        MovieSession movieSession1 = new MovieSession();
        movieSession1.setMovie(fastAndFurious);
        movieSession1.setCinemaHall(yellowHall);
        movieSession1.setShowTime(LocalDateTime.of(2024, 9,10, 10, 10));

        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(fastAndFurious);
        movieSession2.setCinemaHall(greenHall);
        movieSession2.setShowTime(LocalDateTime.of(2024, 9,10, 10, 10));
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession1);
        movieSessionService.add(movieSession2);

        System.out.println("sessions found : ");
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2024, 9, 10))
                .forEach(System.out::println);
    }
}
