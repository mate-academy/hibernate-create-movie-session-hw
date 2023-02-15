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
        Movie lordOfTheRings = new Movie("Lord of the Rings");
        lordOfTheRings.setDescription("Fantasy");
        movieService.add(lordOfTheRings);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall smallCinemaHall = new CinemaHall();
        smallCinemaHall.setCapacity(60);
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

        MovieSession firstMovieSession = new MovieSession();
        firstMovieSession.setMovie(fastAndFurious);
        firstMovieSession.setCinemaHall(smallCinemaHall);
        firstMovieSession.setShowTime(LocalDateTime.of(2023, 2, 24, 00, 00));
        MovieSession secondMovieSession = new MovieSession();
        secondMovieSession.setMovie(lordOfTheRings);
        secondMovieSession.setCinemaHall(imaxCinemaHall);
        secondMovieSession.setShowTime(LocalDateTime.of(2023, 2, 15, 20, 30));
        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(secondMovieSession);
        movieSessionService.add(firstMovieSession);
        movieSessionService.findAvailableSessions(lordOfTheRings.getId(), LocalDate.of(2023, 2, 16))
                .forEach(System.out::println);
    }
}
