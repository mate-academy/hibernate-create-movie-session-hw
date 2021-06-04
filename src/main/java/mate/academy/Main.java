package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final CinemaHallService cinemaHallService
            = (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieService movieService
            = (MovieService) injector.getInstance(MovieService.class);
    private static final MovieSessionService movieSessionService
            = (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        CinemaHall redHall = new CinemaHall();
        redHall.setCapacity(120);
        redHall.setDescription("Big hall");
        cinemaHallService.add(redHall);
        CinemaHall blueHall = new CinemaHall();
        blueHall.setCapacity(30);
        blueHall.setDescription("Small hall");
        cinemaHallService.add(blueHall);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        Movie ironMan = new Movie("Iron Man");
        ironMan.setDescription("Movie from Marvel Universe");
        movieService.add(ironMan);

        MovieSession firstSession = new MovieSession();
        firstSession.setMovie(fastAndFurious);
        firstSession.setCinemaHall(redHall);
        firstSession.setShowTime(LocalDateTime.of(2021, 5, 30, 10, 0));
        movieSessionService.add(firstSession);
        MovieSession secondSession = new MovieSession();
        secondSession.setMovie(ironMan);
        secondSession.setCinemaHall(redHall);
        secondSession.setShowTime(LocalDateTime.of(2021, 5, 30, 13, 0));
        movieSessionService.add(secondSession);
        MovieSession thirdSession = new MovieSession();
        thirdSession.setMovie(ironMan);
        thirdSession.setCinemaHall(blueHall);
        thirdSession.setShowTime(LocalDateTime.of(2021, 5, 30, 10, 0));
        movieSessionService.add(thirdSession);
        MovieSession fourthSession = new MovieSession();
        fourthSession.setMovie(ironMan);
        fourthSession.setCinemaHall(blueHall);
        fourthSession.setShowTime(LocalDateTime.of(2021, 5, 28, 21, 0));
        movieSessionService.add(fourthSession);

        System.out.println("----------------------------------------");
        System.out.println("Cinema Hall service test");
        System.out.println("----------------------------------------");
        System.out.println(cinemaHallService.get(redHall.getId()));
        System.out.println(cinemaHallService.get(blueHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        System.out.println("----------------------------------------");
        System.out.println("Movie service test");
        System.out.println("----------------------------------------");
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(movieService.get(ironMan.getId()));
        movieService.getAll().forEach(System.out::println);

        System.out.println("----------------------------------------");
        System.out.println("Movie session service test");
        System.out.println("----------------------------------------");
        System.out.println(movieSessionService.get(firstSession.getId()));
        System.out.println(movieSessionService.get(secondSession.getId()));
        System.out.println(movieSessionService.get(thirdSession.getId()));
        System.out.println(movieSessionService.get(fourthSession.getId()));
        List<MovieSession> availableSessions
                = movieSessionService.findAvailableSessions(ironMan.getId(), LocalDate.of(2021, 5,
                        30));
        availableSessions.forEach(System.out::println);
    }
}
