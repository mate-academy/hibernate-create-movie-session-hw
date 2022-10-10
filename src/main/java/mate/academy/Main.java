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
        Injector injector = Injector.getInstance("mate");

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(60);
        cinemaHall.setDescription("Hall for Movies");
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        LocalDateTime showTime = LocalDateTime.of(2022, 10, 12, 16, 0, 0);
        MovieSession firstMovieSession = new MovieSession(fastAndFurious, cinemaHall, showTime);
        movieSessionService.add(firstMovieSession);
        System.out.println(movieSessionService.get(firstMovieSession.getId()));
        MovieSession secondMovieSession = new MovieSession(fastAndFurious, cinemaHall,
                showTime.plusMinutes(30L));
        movieSessionService.add(secondMovieSession);
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2022, 10, 12)).forEach(System.out::println);
    }
}
