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
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall redCinemaHall = new CinemaHall();
        redCinemaHall.setCapacity(100);
        redCinemaHall.setDescription("Red");
        cinemaHallService.add(redCinemaHall);
        System.out.println(redCinemaHall.getId());
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession mondaymovieSession = new MovieSession();
        mondaymovieSession.setCinemaHall(redCinemaHall);
        mondaymovieSession.setMovie(fastAndFurious);
        mondaymovieSession.setShowTime(LocalDateTime.now().minusDays(1));

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(mondaymovieSession);
        System.out.println(mondaymovieSession.getId());
        movieSessionService.findAvailableSessions(mondaymovieSession.getId(),
                        LocalDate.now().minusDays(1))
                .forEach(System.out::println);
    }
}
