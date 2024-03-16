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
        Injector injector = Injector.getInstance("mate.academy");
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie oppenheimer = new Movie("Oppenheimer");
        oppenheimer.setDescription("Epic biographical thriller film");
        movieService.add(oppenheimer);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall cinemaBlueHall = new CinemaHall(16, "Blue Hall");
        cinemaHallService.add(cinemaBlueHall);
        CinemaHall cinemaRedHall = new CinemaHall(12, "Red Hall");
        cinemaHallService.add(cinemaRedHall);

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        MovieSession dayMovieSession = new MovieSession(
                LocalDateTime.of(2024, 3, 16, 12, 20));
        dayMovieSession.setMovie(fastAndFurious);
        dayMovieSession.setCinemaHall(cinemaBlueHall);
        movieSessionService.add(dayMovieSession);

        MovieSession oppenheimerMovieSession = new MovieSession(
                LocalDateTime.of(2024, 3, 16, 12, 20));
        oppenheimerMovieSession.setMovie(oppenheimer);
        oppenheimerMovieSession.setCinemaHall(cinemaBlueHall);
        movieSessionService.add(oppenheimerMovieSession);

        MovieSession eveningMovieSession = new MovieSession(
                LocalDateTime.of(2024, 3, 16, 18, 30));
        eveningMovieSession.setMovie(fastAndFurious);
        eveningMovieSession.setCinemaHall(cinemaRedHall);
        movieSessionService.add(eveningMovieSession);

        MovieSession nightMovieSession = new MovieSession(
                LocalDateTime.of(2024, 3, 17, 2, 20));
        nightMovieSession.setMovie(fastAndFurious);
        nightMovieSession.setCinemaHall(cinemaBlueHall);
        movieSessionService.add(nightMovieSession);

        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        cinemaHallService.getAll().forEach(System.out::println);

        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2024, 3, 16)).forEach(System.out::println);
    }
}
