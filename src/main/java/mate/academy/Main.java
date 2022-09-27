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
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie taxi = new Movie("Taxi");
        taxi.setDescription("Very speed movie");
        movieService.add(taxi);

        movieService.getAll().forEach(System.out::println);

        CinemaHall hall3D = new CinemaHall();
        hall3D.setCapacity(100);
        hall3D.setDescription("big hall with 3D technology");
        CinemaHall hallImax = new CinemaHall();
        hallImax.setCapacity(150);
        hallImax.setDescription("with IMAX technology");
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        cinemaHallService.add(hall3D);
        cinemaHallService.add(hallImax);
        System.out.println(cinemaHallService.getAll());

        MovieSession movieSessionTaxi = new MovieSession();
        movieSessionTaxi.setMovie(taxi);
        movieSessionTaxi.setCinemaHall(hall3D);
        movieSessionTaxi.setShowTime(LocalDateTime.of(2022,
                9, 26, 12, 00));
        MovieSession movieSessionFaf = new MovieSession();
        movieSessionFaf.setMovie(fastAndFurious);
        movieSessionFaf.setCinemaHall(hall3D);
        movieSessionFaf.setShowTime(LocalDateTime.of(2022,
                9, 26, 14, 00));
        MovieSession movieSessionFafImax = new MovieSession();
        movieSessionFafImax.setMovie(fastAndFurious);
        movieSessionFafImax.setCinemaHall(hallImax);
        movieSessionFafImax.setShowTime(LocalDateTime.of(2022,
                9, 26, 14, 00));
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(movieSessionTaxi);
        movieSessionService.add(movieSessionFaf);
        movieSessionService.add(movieSessionFafImax);
        System.out.println(movieSessionService.get(2L));
        System.out.println(movieSessionService.findAvailableSessions(1L,
                LocalDate.of(2022, 9, 26)));
    }
}
