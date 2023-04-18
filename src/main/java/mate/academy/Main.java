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
    private static final int LONDON_CAPACITY = 30;
    private static final int TOKIO_CAPACITY = 32;
    private static final int PARIS_CAPACITY = 7;
    private static final String PARIS = "Paris";
    private static final String LONDON = "London";
    private static final String TOKIO = "Tokio";
    private static Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        Movie terminator = new Movie("Terminator");
        Movie snitch = new Movie("Snitch");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        terminator.setDescription("An action and fantastic film about war against machines.");
        snitch.setDescription("Film is based real evens");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        movieService.add(terminator);
        movieService.add(snitch);

        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall londonHall = new CinemaHall(LONDON_CAPACITY, LONDON);
        CinemaHall parisHall = new CinemaHall(PARIS_CAPACITY, PARIS);
        CinemaHall tokioHall = new CinemaHall(TOKIO_CAPACITY, TOKIO);
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(londonHall);
        cinemaHallService.add(parisHall);
        cinemaHallService.add(tokioHall);

        System.out.println(cinemaHallService.get(londonHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession firstSession =
                new MovieSession(terminator, londonHall, LocalDateTime.now().plusHours(2));
        MovieSession secondSession =
                new MovieSession(snitch, parisHall, LocalDateTime.now().plusHours(3));
        MovieSession thirdSession =
                new MovieSession(fastAndFurious, tokioHall, LocalDateTime.now().plusHours(4));
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(firstSession);
        movieSessionService.add(secondSession);
        movieSessionService.add(thirdSession);

        System.out.println(movieSessionService.get(secondSession.getId()));
        movieSessionService.findAvailableSessions(terminator.getId(), LocalDate.now())
                .forEach(System.out::println);
    }
}
