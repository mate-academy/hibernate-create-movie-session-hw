package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;
import java.time.LocalDateTime;

public class Main {
    private static Injector injector = Injector.getInstance("mate.academy");
    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie();
        fastAndFurious.setTitle("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        CinemaHall smallCinemaHall = new CinemaHall();
        smallCinemaHall.setCapacity(100);
        smallCinemaHall.setDescription("Small hall");
        cinemaHallService.add(smallCinemaHall);
        System.out.println(cinemaHallService.get(smallCinemaHall.getId()));

        CinemaHall largeCinemaHall = new CinemaHall();
        largeCinemaHall.setCapacity(200);
        largeCinemaHall.setDescription("Large hall");
        cinemaHallService.add(largeCinemaHall);
        System.out.println(cinemaHallService.get(largeCinemaHall.getId()));

        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession mondayMovieSession1 = new MovieSession();
        mondayMovieSession1.setMovie(fastAndFurious);
        mondayMovieSession1.setCinemaHall(smallCinemaHall);
        mondayMovieSession1.setShowTime(LocalDateTime
                .of(2023, 11, 23, 14,30));
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(mondayMovieSession1);
        System.out.println(movieSessionService.get(mondayMovieSession1.getId()));
    }
}
