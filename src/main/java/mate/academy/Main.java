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
    private static Injector injector = Injector.getInstance("mate");
    private static MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static MovieSessionService sessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie();
        fastAndFurious.setTitle("Fast and Furious");
        Movie shrek = new Movie();
        shrek.setTitle("Shrek 1");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        shrek.setDescription("First shrek movie");
        movieService.add(fastAndFurious);
        movieService.add(shrek);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall blueHall = new CinemaHall();
        blueHall.setCapacity(100);
        blueHall.setDescription("Blue hall");
        cinemaHallService.add(blueHall);
        System.out.println(cinemaHallService.get(blueHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession januarySession = new MovieSession();
        januarySession.setMovie(fastAndFurious);
        januarySession.setCinemaHall(blueHall);
        januarySession.setShowTime(LocalDateTime.parse("2021-01-10T10:15:00"));

        MovieSession shrekJanuarySession = new MovieSession();
        shrekJanuarySession.setMovie(shrek);
        shrekJanuarySession.setCinemaHall(blueHall);
        shrekJanuarySession.setShowTime(LocalDateTime.parse("2021-01-10T14:15:00"));
        sessionService.add(shrekJanuarySession);
        MovieSession anotherShrekJanuarySession = new MovieSession();
        anotherShrekJanuarySession.setMovie(shrek);
        anotherShrekJanuarySession.setCinemaHall(blueHall);
        anotherShrekJanuarySession.setShowTime(LocalDateTime.parse("2021-01-10T10:15:00"));
        sessionService.add(anotherShrekJanuarySession);
        System.out.println(sessionService.get(anotherShrekJanuarySession.getId()));

        MovieSession marchSession = new MovieSession();
        marchSession.setMovie(fastAndFurious);
        marchSession.setCinemaHall(blueHall);
        marchSession.setShowTime(LocalDateTime.parse("2021-03-10T10:15:00"));
        sessionService.add(marchSession);

        sessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDate.parse("2021-01-10"))
                .forEach(System.out::println);
        sessionService
                .findAvailableSessions(shrek.getId(), LocalDate.parse("2021-01-10"))
                .forEach(System.out::println);
    }
}
