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
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        Movie dancers = new Movie("Dancers");
        dancers.setDescription("A comedy film about dancers.");
        movieService.add(dancers);
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall smallCinemaHall = new CinemaHall();
        smallCinemaHall.setCapacity(20);
        smallCinemaHall.setDescription("Small cinema hall");
        cinemaHallService.add(smallCinemaHall);
        System.out.println(cinemaHallService.get(smallCinemaHall.getId()));
        CinemaHall mediumCinemaHall = new CinemaHall();
        mediumCinemaHall.setCapacity(50);
        mediumCinemaHall.setDescription("Medium cinema hall");
        cinemaHallService.add(mediumCinemaHall);
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSession1 = new MovieSession();
        movieSession1.setMovie(fastAndFurious);
        movieSession1.setCinemaHall(smallCinemaHall);
        movieSession1.setShowTime(LocalDateTime.of(2024, 7, 28, 15, 0));
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession1);
        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(fastAndFurious);
        movieSession2.setCinemaHall(smallCinemaHall);
        movieSession2.setShowTime(LocalDateTime.of(2024, 7, 28, 22, 15));
        movieSessionService.add(movieSession2);
        MovieSession movieSession3 = new MovieSession();
        movieSession3.setMovie(fastAndFurious);
        movieSession3.setCinemaHall(smallCinemaHall);
        movieSession3.setShowTime(LocalDateTime.of(2024, 7, 29, 22, 15));
        movieSessionService.add(movieSession3);
        System.out.println(movieSessionService.get(movieSession3.getId()));
        System.out.println(movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDate.of(2024, 7, 28)));
    }
}
