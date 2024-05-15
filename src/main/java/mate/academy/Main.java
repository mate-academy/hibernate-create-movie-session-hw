package mate.academy;

import java.time.LocalDate;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector INJECTOR =
            Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService =
                (MovieService) INJECTOR.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie fastAndFurious2 = new Movie("Fast and Furious 2");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious2);

        Movie fastAndFurious3 = new Movie("Fast and Furious 3");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious3);
        System.out.println(movieService.get(fastAndFurious2.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService sinemaHallService =
                (CinemaHallService) INJECTOR.getInstance(CinemaHallService.class);

        CinemaHall cinemaHall1 = new CinemaHall();
        cinemaHall1.setCapacity(100);
        cinemaHall1.setDescription("Small and comfortable cinema hall");
        sinemaHallService.add(cinemaHall1);

        CinemaHall cinemaHall2 = new CinemaHall();
        cinemaHall2.setCapacity(350);
        cinemaHall2.setDescription("Biggest hall in cinema");
        sinemaHallService.add(cinemaHall2);
        System.out.println(sinemaHallService.get(cinemaHall1.getId()));

        MovieSession movieSession = new MovieSession();
        movieSession.setCinemaHall(cinemaHall1);
        movieSession.setShowTime(LocalDate.now());
        movieSession.setMovie(fastAndFurious2);
        MovieSessionService movieSessionService =
                (MovieSessionService) INJECTOR.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(movieSession.getId()));
    }
}
