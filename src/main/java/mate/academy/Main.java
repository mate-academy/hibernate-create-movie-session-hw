package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie findingNemo = new Movie("Finding Nemo",
                "Sweet father-son tale has some very scary moments.");
        movieService.add(findingNemo);
        System.out.println(movieService.get(findingNemo.getId()));
        Movie despicableMe = new Movie("Despicable Me",
                "Clever, funny, and sweet villain-with-a-heart-of-gold tale.");
        movieService.add(despicableMe);
        movieService.getAll().forEach(System.out::println);

        CinemaHall blueHall = new CinemaHall(150, "Hall for children");
        cinemaHallService.add(blueHall);
        System.out.println(cinemaHallService.get(blueHall.getId()));
        CinemaHall greenFall = new CinemaHall(200, "Hall for family");
        cinemaHallService.add(greenFall);
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession sessionFindingBlue1512 = new MovieSession(
                LocalDateTime.of(LocalDate.of(2021, 11, 15),
                        LocalTime.of(12, 0)),
                findingNemo, blueHall);
        movieSessionService.add(sessionFindingBlue1512);
        System.out.println(movieSessionService.get(sessionFindingBlue1512.getId()));
        MovieSession sessionFindingGreen1515 = new MovieSession(
                LocalDateTime.of(LocalDate.of(2021, 11, 15),
                        LocalTime.of(15, 0)),
                findingNemo, greenFall);
        movieSessionService.add(sessionFindingGreen1515);
        MovieSession sessionDespicableBlue1515 = new MovieSession(
                LocalDateTime.of(LocalDate.of(2021, 11, 15),
                        LocalTime.of(15, 0)),
                despicableMe, blueHall);
        movieSessionService.add(sessionDespicableBlue1515);
        MovieSession sessionFindingBlue1612 = new MovieSession(
                LocalDateTime.of(LocalDate.of(2021, 11, 16),
                        LocalTime.of(12, 0)),
                findingNemo, blueHall);
        movieSessionService.add(sessionFindingBlue1612);
        movieSessionService.findAvailableSessions(findingNemo.getId(),
                LocalDate.of(2021, 11, 15))
                .forEach(System.out::println);
    }
}
