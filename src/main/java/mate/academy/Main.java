package mate.academy;

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
        final MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie kingKong = new Movie("King Kong");
        fastAndFurious.setDescription("An action film about big monkey.");
        movieService.add(fastAndFurious);
        movieService.add(kingKong);
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(movieService.get(kingKong.getId()));
        movieService.getAll().forEach(System.out::println);

        final CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
        CinemaHall cinemaHallSmall = new CinemaHall();
        cinemaHallSmall.setCapacity(100);
        cinemaHallSmall.setDescription("Small cinema hall");
        CinemaHall cinemaHallBig = new CinemaHall();
        cinemaHallBig.setCapacity(500);
        cinemaHallBig.setDescription("Big cinema hall");
        cinemaHallService.add(cinemaHallSmall);
        cinemaHallService.add(cinemaHallBig);
        System.out.println(cinemaHallService.get(cinemaHallSmall.getId()));
        System.out.println(cinemaHallService.get(cinemaHallBig.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        final MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        MovieSession movieSession1 = new MovieSession();
        movieSession1.setMovie(kingKong);
        movieSession1.setCinemaHall(cinemaHallSmall);
        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(fastAndFurious);
        movieSession2.setCinemaHall(cinemaHallBig);

    }
}
