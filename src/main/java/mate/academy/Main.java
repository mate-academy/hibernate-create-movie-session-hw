package mate.academy;

import java.time.LocalDate;
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
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie fifthElement = new Movie("Fifth element");
        fifthElement.setDescription("Great cinema with Bruce Willis");
        movieService.add(fifthElement);

        Movie titanic = new Movie("Titanic");
        titanic.setDescription("About love");
        movieService.add(titanic);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall redHall = new CinemaHall();
        redHall.setDescription("Red Hall");
        redHall.setCapacity(100);
        redHall = cinemaHallService.add(redHall);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(redHall);
        movieSession.setShowTime(LocalDateTime.of(2023, 6, 27, 19, 55));

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);

        CinemaHall blueHall = new CinemaHall();
        blueHall.setDescription("Blue Hall");
        blueHall.setCapacity(200);
        blueHall = cinemaHallService.add(blueHall);

        MovieSession fifthElementSession = new MovieSession();
        fifthElementSession.setMovie(fifthElement);
        fifthElementSession.setCinemaHall(blueHall);
        fifthElementSession.setShowTime(LocalDateTime.of(2023, 6, 27, 17, 55));
        movieSessionService.add(fifthElementSession);

        CinemaHall greenHall = new CinemaHall();
        greenHall.setDescription("Green Hall");
        greenHall.setCapacity(300);
        greenHall = cinemaHallService.add(greenHall);

        MovieSession titanicSession = new MovieSession();
        titanicSession.setMovie(titanic);
        titanicSession.setCinemaHall(greenHall);
        titanicSession.setShowTime(LocalDateTime.of(2023, 6, 28, 10, 30));
        movieSessionService.add(titanicSession);

        MovieSession titanicSecondSession = new MovieSession();
        titanicSecondSession.setMovie(titanic);
        titanicSecondSession.setCinemaHall(redHall);
        titanicSecondSession.setShowTime(LocalDateTime.of(2023, 6, 28, 12, 30));
        movieSessionService.add(titanicSecondSession);

        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        movieSessionService.findAvailableSessions(3L, LocalDate.of(2023, 6, 28))
                .forEach(System.out::println);
    }
}
