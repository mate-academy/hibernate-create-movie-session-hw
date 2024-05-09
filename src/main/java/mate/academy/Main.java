package mate.academy;

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
        Movie fastAndFurious = new Movie();
        fastAndFurious.setTitle("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");

        Movie jurassicPark = new Movie();
        jurassicPark.setTitle("Jurassic Park");
        jurassicPark.setDescription("A movie about dino");

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        movieService.add(jurassicPark);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall greenHall = new CinemaHall();
        greenHall.setCapacity(200);
        greenHall.setDescription("new hall");

        CinemaHall purpleHall = new CinemaHall();
        purpleHall.setCapacity(60);
        purpleHall.setDescription("old and small hall");

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        cinemaHallService.add(greenHall);
        cinemaHallService.add(purpleHall);
        System.out.println(cinemaHallService.get(greenHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession firstSession = new MovieSession();
        firstSession.setMovie(fastAndFurious);
        firstSession.setCinemaHall(purpleHall);
        firstSession.setShowTime(LocalDateTime.of(2024, 5, 3, 20, 30));

        MovieSession secondSession = new MovieSession();
        secondSession.setMovie(jurassicPark);
        secondSession.setCinemaHall(greenHall);
        secondSession.setShowTime(LocalDateTime.of(2024, 5, 9, 19, 0));

        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(firstSession);
        movieSessionService.add(secondSession);
        System.out.println(movieSessionService.findAvailableSessions(jurassicPark.getId(),
                secondSession.getShowTime().toLocalDate()));
    }
}
