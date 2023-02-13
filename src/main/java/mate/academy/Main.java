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
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        Movie avatar2 = new Movie("Avatar: The Way of Water");
        avatar2.setDescription("Continuation of the story about the Na'vi race on Pandora");
        movieService.add(avatar2);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall smallCinemaHall = new CinemaHall();
        smallCinemaHall.setCapacity(50);
        smallCinemaHall.setDescription("small hall");
        CinemaHall imaxCinemaHall = new CinemaHall();
        imaxCinemaHall.setCapacity(150);
        imaxCinemaHall.setDescription("IMAX");
        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(smallCinemaHall);
        cinemaHallService.add(imaxCinemaHall);
        System.out.println(cinemaHallService.get(imaxCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession morningMovieSession = new MovieSession();
        morningMovieSession.setMovie(fastAndFurious);
        morningMovieSession.setCinemaHall(smallCinemaHall);
        morningMovieSession.setShowTime(LocalDateTime.of(2023, 2, 14, 12, 00));
        MovieSession eveningMovieSession = new MovieSession();
        eveningMovieSession.setMovie(avatar2);
        eveningMovieSession.setCinemaHall(imaxCinemaHall);
        eveningMovieSession.setShowTime(LocalDateTime.of(2023, 2, 14, 19, 00));
        MovieSession secondEveningMovieSession = new MovieSession();
        secondEveningMovieSession.setMovie(avatar2);
        secondEveningMovieSession.setCinemaHall(imaxCinemaHall);
        secondEveningMovieSession.setShowTime(LocalDateTime.of(2023, 2, 15, 19, 00));
        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(eveningMovieSession);
        movieSessionService.add(morningMovieSession);
        movieSessionService.add(secondEveningMovieSession);
        System.out.println(movieSessionService.get(secondEveningMovieSession.getId()));
        System.out.println("Main finished");
        movieSessionService.findAvailableSessions(avatar2.getId(), LocalDate.of(2023, 2, 14))
                .forEach(System.out::println);
    }
}
