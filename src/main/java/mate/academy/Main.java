package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private final static Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        CinemaHall yellowCinemaHall = new CinemaHall();
        yellowCinemaHall.setDescription("Yellow");
        yellowCinemaHall.setCapacity(1000);
        cinemaHallService.add(yellowCinemaHall);
        System.out.println(cinemaHallService.get(yellowCinemaHall.getId()));
        CinemaHall blueCinemaHall = new CinemaHall();
        blueCinemaHall.setDescription("Blue");
        blueCinemaHall.setCapacity(2000);
        cinemaHallService.add(blueCinemaHall);
        System.out.println(cinemaHallService.get(blueCinemaHall.getId()));
        System.out.println(cinemaHallService.getAll());

    }
}
