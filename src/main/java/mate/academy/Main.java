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

        Movie venom = new Movie("Venom");
        venom.setDescription("Excellent action film with Tom Hardy in main role"
                + ", good choice for look");
        movieService.add(venom);
        System.out.println(movieService.get(venom.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall yourChoice = new CinemaHall("Your choice");
        yourChoice.setCapacity(1000);
        yourChoice.setDescription("The most innovation cinema hall in the city");
        cinemaHallService.add(yourChoice);
        System.out.println(cinemaHallService.get(yourChoice.getId()));

        MovieSession inYourChoice = new MovieSession();
        inYourChoice.setMovie(venom);
        inYourChoice.setCinemaHall(yourChoice);
        inYourChoice.setShowTime(LocalDateTime.of(2023, 7, 24, 5, 30));
        movieSessionService.add(inYourChoice);
        System.out.println(movieSessionService.get(inYourChoice.getId()));
    }
}
