package mate.academy;

import java.time.LocalDateTime;
import mate.academy.lib.Inject;
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
        @Inject
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        Movie avatar = new Movie("Avatar");
        avatar.setDescription("Avatar became one of the highest grossing movies of all time.");
        movieService.add(avatar);
        System.out.println(movieService.get(avatar.getId()));
        movieService.getAll().forEach(System.out::println);

        @Inject
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall cinemaHall1 = new CinemaHall();
        cinemaHall1.setCapacity(30);
        cinemaHall1.setDescription("small and comfortable");
        cinemaHallService.add(cinemaHall1);
        System.out.println(cinemaHallService.get(cinemaHall1.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSession1 = new MovieSession();
        movieSession1.setMovie(avatar);
        movieSession1.setCinemaHall(cinemaHall1);
        movieSession1.setShowTime(LocalDateTime.of(2022, 9, 8, 20, 40));

        @Inject
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        movieSessionService.add(movieSession1);
        System.out.println(movieSessionService.get(movieSession1.getId()));
        movieSessionService.findAvailableSessions(avatar.getId(),
                movieSession1.getShowTime().toLocalDate()).forEach(System.out::println);
    }
}
