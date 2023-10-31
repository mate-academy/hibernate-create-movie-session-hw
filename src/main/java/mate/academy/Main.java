package mate.academy;

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
        MovieService movieService
                = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall hallOfAnnaUzhvenko =
                new CinemaHall();
        hallOfAnnaUzhvenko.setCapacity(500);
        hallOfAnnaUzhvenko.setDescription("hall of a beautiful girl");
        cinemaHallService.add(hallOfAnnaUzhvenko);
        System.out.println(cinemaHallService.get(hallOfAnnaUzhvenko.getId()));
        CinemaHall royalCinema = new CinemaHall();
        royalCinema.setCapacity(600);
        royalCinema.setDescription("it's a elegance cinema");
        cinemaHallService.add(royalCinema);
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession firstMovieSession = new MovieSession();
        firstMovieSession.setMovie(fastAndFurious);
        firstMovieSession.setCinemaHall(hallOfAnnaUzhvenko);
        firstMovieSession.setLocalDateTime(LocalDateTime.now());
        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(firstMovieSession);
        System.out.println(movieSessionService.get(firstMovieSession.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDateTime.now().toLocalDate()).forEach(System.out::println);
    }
}
