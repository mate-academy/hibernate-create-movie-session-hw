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
    private static final Injector injector
            = Injector.getInstance("mate.academy");
    private static MovieService movieService
            = (MovieService) injector.getInstance(MovieService.class);
    private static CinemaHallService cinemaHallService
            = (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static MovieSessionService movieSessionService
            = (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("Action movie with a lot of racing");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie lotr = new Movie("Lord of the Rings");
        lotr.setDescription("Fantasy movie about the adventures a hobbit");
        movieService.add(lotr);
        System.out.println(movieService.get(lotr.getId()));

        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall(70, "Blue cinema Hall");
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        LocalDateTime sessionDate = LocalDateTime.of(2023, 7, 7, 12, 00);
        LocalDateTime sessionDate02 = LocalDateTime.of(2023, 7, 7, 20, 00);
        MovieSession movieSession = new MovieSession(fastAndFurious, cinemaHall, sessionDate);
        MovieSession movieSession02 = new MovieSession(fastAndFurious, cinemaHall, sessionDate02);
        movieSessionService.add(movieSession);
        movieSessionService.add(movieSession02);
        System.out.println(movieSessionService.get(movieSession.getId()));
        System.out.println(movieSessionService.get(movieSession02.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2023, 7, 7)).forEach(System.out::println);
    }
}
