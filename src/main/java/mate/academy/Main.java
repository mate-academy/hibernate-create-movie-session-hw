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
        Movie fastAndFurious = new Movie();
        fastAndFurious.setTitle("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie homeAlone = new Movie();
        homeAlone.setTitle("Home alone");
        homeAlone.setDescription("Christmas comedy");

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        movieService.add(homeAlone);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall firstHall = new CinemaHall();
        firstHall.setCapacity(100);
        firstHall.setDescription("First hall");
        CinemaHall secondHall = new CinemaHall();
        secondHall.setCapacity(200);
        secondHall.setDescription("Second hall");

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(firstHall);
        cinemaHallService.add(secondHall);
        System.out.println(cinemaHallService.get(secondHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession firstSession = new MovieSession();
        firstSession.setMovie(fastAndFurious);
        firstSession.setCinemaHall(firstHall);
        firstSession.setShowTime(LocalDateTime.now());

        MovieSession secondSession = new MovieSession();
        secondSession.setMovie(homeAlone);
        secondSession.setCinemaHall(secondHall);
        secondSession.setShowTime(LocalDateTime.now());

        MovieSession thirdSession = new MovieSession();
        thirdSession.setMovie(homeAlone);
        thirdSession.setCinemaHall(firstHall);
        thirdSession.setShowTime(LocalDateTime.now().plusDays(1L));

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(firstSession);
        movieSessionService.add(secondSession);
        movieSessionService.add(thirdSession);
        System.out.println(movieSessionService.get(firstSession.getId()));
        movieSessionService.findAvailableSessions(homeAlone.getId(),
                LocalDate.now()).forEach(System.out::println);
    }
}
