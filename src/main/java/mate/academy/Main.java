package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import mate.academy.lib.Inject;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    private static final MovieService MOVIESERVICE
            = (MovieService) INJECTOR.getInstance(MovieService.class);

    @Inject
    private static final CinemaHallService CINEMAHALLSERVICE
            = (CinemaHallService) INJECTOR.getInstance(CinemaHallService.class);

    @Inject
    private static final MovieSessionService MOVIESESSIONSERVICE
            = (MovieSessionService) INJECTOR.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        MOVIESERVICE.add(fastAndFurious);
        System.out.println(MOVIESERVICE.get(fastAndFurious.getId()));
        MOVIESERVICE.getAll().forEach(System.out::println);

        CinemaHall firstCinemaHall = new CinemaHall();
        firstCinemaHall.setCapacity(200);
        firstCinemaHall.setDescription("Hall for 200 person");
        CINEMAHALLSERVICE.add(firstCinemaHall);
        System.out.println(CINEMAHALLSERVICE.get(firstCinemaHall.getId()));
        CINEMAHALLSERVICE.getAll().forEach(System.out::println);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(firstCinemaHall);
        movieSession.setShowTime(LocalDateTime.now().plusDays(2));

        MOVIESESSIONSERVICE.add(movieSession);
        System.out.println(MOVIESESSIONSERVICE.get(movieSession.getId()));
        MOVIESESSIONSERVICE.findAvailableSessions(1L, LocalDate.now().plusDays(2))
                .forEach(System.out::println);
    }
}
