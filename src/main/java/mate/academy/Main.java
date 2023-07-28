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

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        MovieService movieService = (MovieService) injector
                .getInstance(MovieService.class);
        movieService.add(fastAndFurious);

        Movie fastAndFuriousTwo = new Movie("Fast and Furious part 2");
        fastAndFuriousTwo.setDescription("An action film about street racing.");
        movieService.add(fastAndFuriousTwo);

        Movie fastAndFuriousThree = new Movie("Fast and Furious part 3");
        fastAndFuriousTwo.setDescription("An action film.");
        movieService.add(fastAndFuriousThree);

        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall blueHall = new CinemaHall();
        blueHall.setDescription("Blue hall");
        blueHall.setCapacity(120);
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        cinemaHallService.add(blueHall);

        CinemaHall redHall = new CinemaHall();
        redHall.setDescription("Red hall");
        redHall.setCapacity(60);
        cinemaHallService.add(redHall);

        System.out.println(cinemaHallService.get(blueHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession morningSession = new MovieSession();
        morningSession.setCinemaHall(blueHall);
        morningSession.setMovie(fastAndFurious);
        morningSession.setShowTime(LocalDateTime.now().plusMinutes(3));
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(morningSession);

        MovieSession afternoonSession = new MovieSession();
        afternoonSession.setCinemaHall(redHall);
        afternoonSession.setMovie(fastAndFuriousTwo);
        afternoonSession.setShowTime(LocalDateTime.now().minusDays(1));
        movieSessionService.add(afternoonSession);

        MovieSession nightSession = new MovieSession();
        nightSession.setCinemaHall(redHall);
        nightSession.setMovie(fastAndFurious);
        nightSession.setShowTime(LocalDateTime.now().minusMinutes(10));
        movieSessionService.add(nightSession);

        System.out.println(movieSessionService.get(morningSession.getId()));
        System.out.println("===============================");
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.now())
                .forEach(System.out::println);
    }
}
