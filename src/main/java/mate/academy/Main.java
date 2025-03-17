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
        final Injector injector = Injector.getInstance("mate.academy");

        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall newHall = new CinemaHall();
        newHall.setCapacity(150);
        cinemaHallService.add(newHall);

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        movieService.getAll().forEach(System.out::println);
        Movie venom = new Movie("Venom");
        venom.setDescription("Very good fantastic film Marvel studio");
        movieService.add(venom);
        LocalDateTime futureDate = LocalDateTime.now().plusDays(8);

        MovieSession movieSession1 = new MovieSession();
        movieSession1.setShowTime(futureDate);
        movieSession1.setMovie(fastAndFurious);
        movieSession1.setCinemaHall(newHall);

        LocalDateTime currentDateTime = LocalDateTime.now();
        MovieSession movieSession2 = new MovieSession();
        movieSession2.setShowTime(currentDateTime);
        movieSession2.setMovie(venom);
        movieSession2.setCinemaHall(newHall);

        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession1);
        movieSessionService.add(movieSession2);
        System.out.println(movieSessionService
                .findAvailableSessions(venom.getId(), currentDateTime.toLocalDate()));

    }
}
