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
        final MovieService movieService = (MovieService)
                Injector.getInstance("mate.academy").getInstance(MovieService.class);
        final CinemaHallService cinemaHallService = (CinemaHallService)
                Injector.getInstance("mate.academy").getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService = (MovieSessionService)
                Injector.getInstance("mate.academy").getInstance(MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie terminator2 = new Movie("Terminator 2");
        terminator2.setDescription("Judgment day");
        movieService.add(terminator2);
        System.out.println(movieService.get(terminator2.getId()));

        movieService.getAll().forEach(System.out::println);

        CinemaHall hall1 = new CinemaHall();
        hall1.setCapacity(100);
        hall1.setDescription("Main hall");
        cinemaHallService.add(hall1);
        System.out.println(cinemaHallService.get(hall1.getId()));

        CinemaHall hall2 = new CinemaHall();
        hall2.setCapacity(50);
        hall2.setDescription("Second hall");
        cinemaHallService.add(hall2);
        System.out.println(cinemaHallService.get(hall2.getId()));

        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession firstSession10Jan = new MovieSession();
        firstSession10Jan.setMovie(fastAndFurious);
        firstSession10Jan.setCinemaHall(hall1);
        firstSession10Jan.setShowTime(LocalDateTime.of(
                2022, 1, 10, 10, 00));
        movieSessionService.add(firstSession10Jan);
        System.out.println(movieSessionService.get(firstSession10Jan.getId()));

        MovieSession secondSession10Jan = new MovieSession();
        secondSession10Jan.setMovie(terminator2);
        secondSession10Jan.setCinemaHall(hall2);
        secondSession10Jan.setShowTime(LocalDateTime.of(
                2022, 1, 10, 12, 00));
        movieSessionService.add(secondSession10Jan);
        System.out.println(movieSessionService.get(secondSession10Jan.getId()));

        MovieSession firstSession11Jan = new MovieSession();
        firstSession11Jan.setMovie(fastAndFurious);
        firstSession11Jan.setCinemaHall(hall1);
        firstSession11Jan.setShowTime(LocalDateTime.of(
                2022, 1, 11, 10, 00));
        movieSessionService.add(firstSession11Jan);
        System.out.println(movieSessionService.get(firstSession11Jan.getId()));

        MovieSession secondSession11Jan = new MovieSession();
        secondSession11Jan.setMovie(terminator2);
        secondSession11Jan.setCinemaHall(hall2);
        secondSession11Jan.setShowTime(LocalDateTime.of(
                2022, 1, 11, 12, 00));
        movieSessionService.add(secondSession11Jan);
        System.out.println(movieSessionService.get(secondSession11Jan.getId()));

        System.out.println(movieSessionService.findAvailableSessions(
                terminator2.getId(), LocalDate.of(2022, 1, 10)));
    }
}
