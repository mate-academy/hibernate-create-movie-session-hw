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
    private static final Injector injector =
            Injector.getInstance("mate.academy");
    private static final MovieService movieService = (MovieService)
            injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService = (CinemaHallService)
            injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService = (MovieSessionService)
            injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        Movie matrix = new Movie("Matrix");
        matrix.setDescription("Neo come to save the world from Machines");
        movieService.add(matrix);
        Movie starWars = new Movie("Star War: New Hope");
        starWars.setDescription("Can someone save universe from Imperial Army?");
        movieService.add(starWars);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall redHall = new CinemaHall();
        redHall.setCapacity(12);
        redHall.setDescription("Small hall");
        cinemaHallService.add(redHall);
        CinemaHall purpleHall = new CinemaHall();
        purpleHall.setCapacity(32);
        purpleHall.setDescription("Big hall for 3D movies");
        cinemaHallService.add(purpleHall);
        System.out.println(cinemaHallService.get(redHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession fastAndFurySession = new MovieSession();
        fastAndFurySession.setCinemaHall(redHall);
        fastAndFurySession.setMovie(fastAndFurious);
        fastAndFurySession.setShowTime(LocalDateTime.of(2021,4,8,15,44));
        movieSessionService.add(fastAndFurySession);
        MovieSession matrixSession = new MovieSession();
        matrixSession.setCinemaHall(purpleHall);
        matrixSession.setMovie(matrix);
        matrixSession.setShowTime(LocalDateTime.of(2021, 4, 8, 19, 45));
        movieSessionService.add(matrixSession);
        MovieSession matrixSecondSession = new MovieSession();
        matrixSession.setCinemaHall(redHall);
        matrixSession.setMovie(matrix);
        matrixSession.setShowTime(LocalDateTime.of(2021, 4, 8, 23, 45));
        movieSessionService.add(matrixSession);
        MovieSession starWarsSession = new MovieSession();
        starWarsSession.setCinemaHall(purpleHall);
        starWarsSession.setMovie(starWars);
        starWarsSession.setShowTime(LocalDateTime.of(2021, 4, 9, 20, 15));
        movieSessionService.add(starWarsSession);
        System.out.println(movieSessionService.get(matrixSession.getId()));

        movieSessionService.findAvailableSessions(matrix.getId(),
                LocalDate.of(2021, 4, 8))
                .forEach(System.out::println);
    }
}
