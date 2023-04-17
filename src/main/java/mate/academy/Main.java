package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate");

    public static void main(String[] args) {
        System.out.println("\nMovieService-----------------------------------------------------\n");
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);

        Movie howToTrainYourDragon = new Movie("How to train your dragon");
        howToTrainYourDragon.setDescription("Viking befriends dragon, saves village. "
                + "Epic adventure, heartwarming bond.");
        movieService.add(howToTrainYourDragon);

        System.out.println("Get by id:\n" + movieService.get(howToTrainYourDragon.getId()));

        System.out.println("Get all:");
        movieService.getAll().forEach(System.out::println);

        System.out.println("\nCinemaHallService------------------------------------------------\n");

        CinemaHall firstCinemaHall = new CinemaHall();
        firstCinemaHall.setCapacity(100);
        firstCinemaHall.setDescription("First cinema hall");
        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(firstCinemaHall);

        CinemaHall secondCinemaHall = new CinemaHall();
        secondCinemaHall.setCapacity(70);
        secondCinemaHall.setDescription("Second cinema hall");
        cinemaHallService.add(secondCinemaHall);

        System.out.println("Get by id:\n" + cinemaHallService.get(firstCinemaHall.getId()));

        System.out.println("Get all:");
        cinemaHallService.getAll().forEach(System.out::println);

        System.out.println("\nMovieSessionService----------------------------------------------\n");

        MovieSession firstSession = new MovieSession();
        firstSession.setCinemaHall(firstCinemaHall);
        firstSession.setMovie(fastAndFurious);
        firstSession.setShowTime(LocalDateTime.now().with(LocalTime.of(14, 0)));
        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        movieSessionService.add(firstSession);

        MovieSession secondSession = new MovieSession();
        secondSession.setCinemaHall(secondCinemaHall);
        secondSession.setMovie(fastAndFurious);
        secondSession.setShowTime(LocalDateTime.now().with(LocalTime.of(17, 0)));
        movieSessionService.add(secondSession);

        System.out.println("Get by id:\n" + movieSessionService.get(secondSession.getId()));

        System.out.println("Find Available Sessions:");
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.now()).forEach(System.out::println);
    }
}
