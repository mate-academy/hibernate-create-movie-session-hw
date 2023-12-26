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
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(20);
        cinemaHallService.add(cinemaHall);

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie kingArtur = new Movie("king Artur");
        kingArtur.setDescription("just a film");
        movieService.add(kingArtur);

        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        LocalDateTime tomorrowDateTime = LocalDateTime.now().plusDays(1);
        MovieSession movieSession1 = new MovieSession();
        movieSession1.setShowTime(tomorrowDateTime);
        movieSession1.setMovie(fastAndFurious);
        movieSession1.setCinemaHall(cinemaHall);

        LocalDateTime currentDateTime = LocalDateTime.now();
        MovieSession movieSession2 = new MovieSession();
        movieSession2.setShowTime(currentDateTime);
        movieSession2.setMovie(kingArtur);
        movieSession2.setCinemaHall(cinemaHall);

        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession1);
        movieSessionService.add(movieSession2);
        System.out.println(movieSessionService
                .findAvailableSessions(kingArtur.getId(), currentDateTime.toLocalDate()));
    }
}
