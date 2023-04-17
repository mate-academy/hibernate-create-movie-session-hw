package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        //movie test
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        //cinema hall test
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall firstHall = new CinemaHall(20L);
        firstHall.setDescription("Default hall");
        cinemaHallService.add(firstHall);
        System.out.println(cinemaHallService.get(firstHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
        //movie session test
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String firstSession = "2023-04-18 11:30";
        MovieSession fastAndFuriousFirst =
                new MovieSession(
                        fastAndFurious,
                        firstHall,
                        LocalDateTime.parse(firstSession, formatter));
        String otherDaySession = "2023-04-19 11:30";
        MovieSession fastAndFuriousSecond =
                new MovieSession(
                        fastAndFurious,
                        firstHall,
                        LocalDateTime.parse(otherDaySession, formatter));
        movieSessionService.add(fastAndFuriousFirst);
        movieSessionService.add(fastAndFuriousSecond);
        System.out.println(movieSessionService.get(fastAndFuriousFirst.getId()));
        System.out.println("Available");
        movieSessionService.findAvailableSessions(
                LocalDate.parse(firstSession, formatter)).forEach(System.out::println);
    }
}
