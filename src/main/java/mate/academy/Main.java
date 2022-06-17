package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;

public class Main {
    private static final Injector inject =
            Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService =
                (MovieService) inject.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) inject.getInstance(CinemaHallService.class);
        CinemaHall standardHall = new CinemaHall();
        standardHall.setCapacity(300);
        standardHall.setDescription("just standard hall.");
        cinemaHallService.add(standardHall);
        cinemaHallService.getAll().forEach(System.out::println);
    }
}
