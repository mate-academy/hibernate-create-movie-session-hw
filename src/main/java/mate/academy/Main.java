package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
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
        // Check working MovieService and MovieDao method
        // First Movie
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        // Second Movie
        Movie fastAndFuriousSecondPart = new Movie("Fast and Furious 2");
        fastAndFuriousSecondPart.setDescription("An action film about street racing, "
                + "heists, and spies.");
        // Checking methods
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        movieService.add(fastAndFuriousSecondPart);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        // Check working CinemaHallService and CinemaHallDao method
        // First CinemaHall
        CinemaHall blueCinemaHall = new CinemaHall();
        blueCinemaHall.setCapacity(35);
        blueCinemaHall.setDescription("Blue cinema hall");
        // Second CinemaHall
        CinemaHall redCinemaHall = new CinemaHall();
        redCinemaHall.setCapacity(50);
        redCinemaHall.setDescription("Red cinema hall");
        // Checking methods
        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(blueCinemaHall);
        cinemaHallService.add(redCinemaHall);
        System.out.println(cinemaHallService.get(blueCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        // Check working MovieSessionService and MovieSessionDao method
        // First MovieSession
        MovieSession firstMovieSession = new MovieSession();
        firstMovieSession.setMovie(fastAndFurious);
        firstMovieSession.setCinemaHall(blueCinemaHall);
        firstMovieSession.setShowTime(LocalDateTime.of(2022, Month.APRIL, 12, 16, 15));
        // Second MovieSession
        MovieSession secondMovieSession = new MovieSession();
        secondMovieSession.setMovie(fastAndFuriousSecondPart);
        secondMovieSession.setCinemaHall(redCinemaHall);
        secondMovieSession.setShowTime(LocalDateTime.of(2022, Month.APRIL, 12, 20, 15));
        // Third MovieSession
        MovieSession thirdMovieSession = new MovieSession();
        thirdMovieSession.setMovie(fastAndFurious);
        thirdMovieSession.setCinemaHall(blueCinemaHall);
        thirdMovieSession.setShowTime(LocalDateTime.of(2022, Month.APRIL, 12, 20, 15));
        // Checking methods
        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(firstMovieSession);
        movieSessionService.add(secondMovieSession);
        movieSessionService.add(thirdMovieSession);
        System.out.println(movieSessionService.get(firstMovieSession.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2022, Month.APRIL, 12)).forEach(System.out::println);
    }
}
