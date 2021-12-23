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
        Injector injector = Injector.getInstance("mate");
        MovieService movieService = (MovieService) injector
                .getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);

        CinemaHall firstHall = new CinemaHall();
        firstHall.setCapacity(50);
        firstHall.setDescription("Good hall");
        cinemaHallService.add(firstHall);
        System.out.println(cinemaHallService.get(firstHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession secondSession = new MovieSession();
        secondSession.setCinemaHall(firstHall);
        secondSession.setMovie(fastAndFurious);
        secondSession.setShowTime(LocalDateTime.of(
                2021, 12, 22, 18, 20));
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(secondSession);
        System.out.println(movieSessionService.get(secondSession.getId()));

        MovieSession firstSession = new MovieSession();
        firstSession.setCinemaHall(firstHall);
        firstSession.setMovie(fastAndFurious);
        firstSession.setShowTime(LocalDateTime.of(
                2021, 12, 22, 16, 20));
        movieSessionService.add(firstSession);
        System.out.println(movieSessionService.get(firstSession.getId()));

        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2021,12,22))
                .forEach(System.out::println);

    }
}
