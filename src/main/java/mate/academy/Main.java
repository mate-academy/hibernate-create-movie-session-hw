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
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);

    public static void main(String[] args) {

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        Movie barbie = new Movie("Barbie ");
        barbie.setDescription("American fantasy comedy film directed by Greta Gerwig"
                + " and written by Gerwig....");
        movieService.add(barbie);
        System.out.println(movieService.get(barbie.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHallCherkassy = new CinemaHall();
        cinemaHallCherkassy.setCapacity(9999);
        cinemaHallCherkassy.setDescription("some description");
        cinemaHallService.add(cinemaHallCherkassy);
        System.out.println(cinemaHallService.get(cinemaHallCherkassy.getId()));

        CinemaHall cinemaHallNeCherkassy = new CinemaHall();
        cinemaHallCherkassy.setCapacity(99);
        cinemaHallCherkassy.setDescription("Godzilla");
        cinemaHallService.add(cinemaHallNeCherkassy);
        System.out.println(cinemaHallService.get(cinemaHallNeCherkassy.getId()));

        MovieSession movieSessionChe = new MovieSession();
        movieSessionChe.setMovie(fastAndFurious);
        movieSessionChe.setCinemaHall(cinemaHallCherkassy);
        movieSessionChe.setLocalDateTime(LocalDateTime.of(2023,7,24,5,32));
        movieSessionService.add(movieSessionChe);
        System.out.println(movieSessionService.get(movieSessionChe.getId()));

        MovieSession movieSessionChe2 = new MovieSession();
        movieSessionChe2.setMovie(barbie);
        movieSessionChe2.setCinemaHall(cinemaHallNeCherkassy);
        movieSessionChe2.setLocalDateTime(LocalDateTime.of(2023,7,25,12,32));
        movieSessionService.add(movieSessionChe2);
        System.out.println(movieSessionService.get(movieSessionChe2.getId()));
    }
}
