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
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println("_____________________________________________________________________");
        movieService.getAll().forEach(System.out::println);
        System.out.println("_____________________________________________________________________");

        CinemaHall greenHall = new CinemaHall();
        greenHall.setCapacity(50);
        greenHall.setDescription("Yellow hall 2D");
        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(greenHall);
        System.out.println(cinemaHallService.get(greenHall.getId()));
        System.out.println("_____________________________________________________________________");

        CinemaHall imaxHall = new CinemaHall();
        imaxHall.setCapacity(100);
        imaxHall.setDescription("Imax hall 3D");
        cinemaHallService.add(imaxHall);
        System.out.println(cinemaHallService.get(imaxHall.getId()));
        System.out.println("_____________________________________________________________________");
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession sessionGreenHall = new MovieSession();
        sessionGreenHall.setMovie(fastAndFurious);
        sessionGreenHall.setCinemaHall(greenHall);
        sessionGreenHall.setShowTime(LocalDateTime
                .of(2022, 7, 22, 19, 30));
        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(sessionGreenHall);
        System.out.println(movieSessionService.get(sessionGreenHall.getId()));
        System.out.println("_____________________________________________________________________");

        MovieSession sessionImaxHall = new MovieSession();
        sessionImaxHall.setMovie(fastAndFurious);
        sessionImaxHall.setCinemaHall(imaxHall);
        sessionImaxHall.setShowTime(LocalDateTime
                .of(2022, 7, 22, 21, 30));
        movieSessionService.add(sessionImaxHall);
        System.out.println(movieSessionService.get(sessionImaxHall.getId()));
        System.out.println("_____________________________________________________________________");
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.parse("2022-07-22")).forEach(System.out::println);
        System.out.println("_____________________________________________________________________");
    }
}
