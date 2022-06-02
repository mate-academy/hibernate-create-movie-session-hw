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
        final MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        final MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        final CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        Movie piratesOfTheCaribbean = new Movie("Pirates of the Caribbean");
        piratesOfTheCaribbean.setDescription("Film about cool pirates");
        movieService.add(piratesOfTheCaribbean);
        movieService.getAll().forEach(System.out::println);

        CinemaHall smallHall = new CinemaHall();
        smallHall.setDescription("Small hall");
        smallHall.setCapacity(10);
        cinemaHallService.add(smallHall);
        System.out.println(cinemaHallService.get(smallHall.getId()));

        CinemaHall bigHall = new CinemaHall();
        bigHall.setDescription("Big hall");
        bigHall.setCapacity(30);
        cinemaHallService.add(bigHall);
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession sessionWithPiratesOfTheCaribbean = new MovieSession();
        sessionWithPiratesOfTheCaribbean.setMovie(piratesOfTheCaribbean);
        sessionWithPiratesOfTheCaribbean.setShowTime(LocalDateTime.now());
        sessionWithPiratesOfTheCaribbean.setCinemaHall(smallHall);
        movieSessionService.add(sessionWithPiratesOfTheCaribbean);
        System.out.println(movieSessionService.get(sessionWithPiratesOfTheCaribbean.getId()));

        MovieSession sessionWithFastAndFurious = new MovieSession();
        sessionWithFastAndFurious.setMovie(fastAndFurious);
        sessionWithFastAndFurious.setShowTime(LocalDateTime.of(2022,7,15,20,30));
        sessionWithFastAndFurious.setCinemaHall(bigHall);
        movieSessionService.add(sessionWithFastAndFurious);

        System.out.println(movieSessionService.findAvailableSessions(piratesOfTheCaribbean.getId(),
                LocalDate.now()));
        System.out.println(movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.now()));
    }
}
