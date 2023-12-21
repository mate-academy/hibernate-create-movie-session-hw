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

        System.out.println();

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        MovieService movieService = (MovieService) injector
                .getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        System.out.println();

        CinemaHall bestCinemaHall = new CinemaHall();
        bestCinemaHall.setCapacity(50);
        bestCinemaHall.setDescription("The best hall in our cinema");
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        cinemaHallService.add(bestCinemaHall);
        System.out.println(cinemaHallService.get(bestCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        System.out.println();

        MovieSession newMovieSession = new MovieSession();
        newMovieSession.setMovie(fastAndFurious);
        newMovieSession.setCinemaHall(bestCinemaHall);
        LocalDateTime showTime = LocalDateTime
                .of(2023, 12, 21, 15, 30);
        newMovieSession.setShowTime(showTime);
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(newMovieSession);
        movieSessionService.get(newMovieSession.getId());
        LocalDate localDate = LocalDate.now();
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), localDate)
                .forEach(System.out::println);
    }
}
