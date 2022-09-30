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
        final MovieService movieService = (MovieService)
                injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");

        System.out.println("-------- ADD MOVIE TO DB: -------->");
        movieService.add(fastAndFurious);

        System.out.println("-------- GET MOVIE BY ID: -------->");
        System.out.println(movieService.get(fastAndFurious.getId()));

        System.out.println("-------- GET ALL MOVIE: -------->");
        movieService.getAll().forEach(System.out::println);

        final CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(90);
        cinemaHall.setDescription("VIP Hall");

        System.out.println("-------- ADD CINEMA HALL TO DB: -------->");
        System.out.println(cinemaHallService.add(cinemaHall));
        System.out.println("-------- GET CINEMA HALL BY ID: -------->");
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        System.out.println("-------- GET ALL CINEMA HALL: -------->");
        cinemaHallService.getAll().forEach(System.out::println);

        final MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setShowTime(LocalDateTime
                .of(2022, 9, 19, 20, 05));

        System.out.println("-------- ADD MOVIE SESSION TO DB: -------->");
        movieSessionService.add(movieSession);

        System.out.println("--------GET MOVIE SESSION BY ID: -------->");
        movieSessionService.get(movieSession.getId());

        System.out.println("--------GET ALL MOVIE SESSION BY ID AND TIME: -------->");
        movieSessionService.findAvailableSessions(
                fastAndFurious.getId(), LocalDate.of(2022, 9, 19));

    }
}
