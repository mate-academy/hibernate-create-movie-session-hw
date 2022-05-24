package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        //firs film
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        //second film
        Movie newYear = new Movie("newYear");
        newYear.setDescription("film about celebrates a new year");
        movieService.add(newYear);

        //cinemaHall red
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall redCinemaHall =
                new CinemaHall(50, "middle red cinemaHall");
        cinemaHallService.add(redCinemaHall);

        //cinemaHall black
        CinemaHall blackCinemaHall =
                new CinemaHall(200, "big black cinemaHall");
        cinemaHallService.add(blackCinemaHall);

        //movieSession current day, first film
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        LocalDateTime afternoonFastAndFurious =
                LocalDateTime.of(LocalDate.now(), LocalTime.of(15, 20));
        MovieSession redMovieSession =
                new MovieSession(fastAndFurious, redCinemaHall, afternoonFastAndFurious);
        movieSessionService.add(redMovieSession);
        System.out.println(movieSessionService.get(redMovieSession.getId()));

        //movieSession current day, first film
        LocalDateTime morningTimeMovie =
                LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 00));
        MovieSession morningMovieSession =
                new MovieSession(fastAndFurious, redCinemaHall, morningTimeMovie);
        movieSessionService.add(morningMovieSession);

        //movieSession current day, second film
        LocalDateTime newYearTimeMovie =
                LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 00));
        MovieSession newYearMovieSession =
                new MovieSession(newYear, blackCinemaHall, newYearTimeMovie);
        movieSessionService.add(newYearMovieSession);

        //movieSession previous day, first film
        LocalDateTime previousDayTimeMovie =
                LocalDateTime.of(LocalDate.of(2022, 05, 21),
                LocalTime.of(10, 00));
        MovieSession previousMovieSession =
                new MovieSession(fastAndFurious, blackCinemaHall, previousDayTimeMovie);
        movieSessionService.add(previousMovieSession);

        List<MovieSession> availableSessionsDay =
                movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.now());
        availableSessionsDay.stream()
                .forEach(System.out::println);
    }
}
