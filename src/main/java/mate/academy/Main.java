package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        Injector instance = Injector.getInstance("mate.academy");

        MovieService movieService = (MovieService) instance.getInstance(MovieService.class);

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

        CinemaHallService sinemaHallService = (CinemaHallService) instance.getInstance(CinemaHallService.class);
        CinemaHall cinemaHall1 = new CinemaHall();
        cinemaHall1.setCapacity(100);
        cinemaHall1.setDescription("Small and comfortable cinema hall");
        sinemaHallService.add(cinemaHall1);

        CinemaHall cinemaHall2 = new CinemaHall();
        cinemaHall2.setCapacity(350);
        cinemaHall2.setDescription("Biggest hall in cinema");
        sinemaHallService.add(cinemaHall2);
        System.out.println(sinemaHallService.get(cinemaHall1.getId()));

        MovieSessionService movieSessionService = (MovieSessionService) instance.getInstance(MovieSessionService.class);
        MovieSession movieSession = new MovieSession();
        movieSession.setCinemaHall(cinemaHall1);
        movieSession.setShowTime(LocalDateTime.now());
        movieSession.setMovie(fastAndFurious2);
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(movieSession.getId()));
    }
}