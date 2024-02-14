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

    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall orangeHall = new CinemaHall();
        orangeHall.setCapacity(200);
        cinemaHallService.add(orangeHall);

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie theGentleman = new Movie("The Gentleman");
        theGentleman.setDescription("Very cool film about Gentleman :)");
        movieService.add(theGentleman);

        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        LocalDateTime futureDate = LocalDateTime.now().plusDays(10);
        MovieSession movieSession1 = new MovieSession();
        movieSession1.setShowTime(futureDate);
        movieSession1.setMovie(fastAndFurious);
        movieSession1.setCinemaHall(orangeHall);

        LocalDateTime currentDateTime = LocalDateTime.now();
        MovieSession movieSession2 = new MovieSession();
        movieSession2.setShowTime(currentDateTime);
        movieSession2.setMovie(theGentleman);
        movieSession2.setCinemaHall(orangeHall);

        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession1);
        movieSessionService.add(movieSession2);
        System.out.println(movieSessionService
                .findAvailableSessions(theGentleman.getId(), currentDateTime.toLocalDate()));
    }
}
