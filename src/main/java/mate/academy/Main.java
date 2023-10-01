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
        Injector instance = Injector.getInstance("mate.academy");
        MovieService movieService = (MovieService) instance.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService) instance.getInstance(
                CinemaHallService.class);
        CinemaHall cinemaHallBig = new CinemaHall();
        cinemaHallBig.setCapacity(200);
        cinemaHallBig.setDescription("big Hall");
        cinemaHallService.add(cinemaHallBig);
        CinemaHall cinemaHallSmall = new CinemaHall();
        cinemaHallSmall.setCapacity(100);
        cinemaHallSmall.setDescription("small hall");
        cinemaHallService.add(cinemaHallSmall);
        System.out.println(cinemaHallService.get(cinemaHallBig.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHallBig);
        movieSession.setShowTime(LocalDateTime.of(
                2023, 10, 1, 10, 0));
        MovieSessionService movieSessionService = (MovieSessionService) instance.getInstance(
                MovieSessionService.class);
        movieSessionService.add(movieSession);
        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(fastAndFurious);
        movieSession2.setCinemaHall(cinemaHallBig);
        movieSession2.setShowTime(LocalDateTime.of(
                2023, 10, 2, 10, 0));
        movieSessionService.add(movieSession2);
        System.out.println(movieSessionService.findAvailableSessions(
                fastAndFurious.getId(), LocalDate.of(2023, 10, 1)));
    }
}
